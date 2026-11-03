package com.mangoloads.currentlens

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

internal enum class StatsDateRange(val label: String) {
    TODAY("Today"),
    LAST_7_DAYS("7 days"),
    LAST_30_DAYS("30 days"),
    ALL_TIME("All time");

    fun includes(date: LocalDate, today: LocalDate): Boolean = when (this) {
        TODAY -> date == today
        LAST_7_DAYS -> !date.isBefore(today.minusDays(6)) && !date.isAfter(today)
        LAST_30_DAYS -> !date.isBefore(today.minusDays(29)) && !date.isAfter(today)
        ALL_TIME -> !date.isAfter(today)
    }
}

internal data class ReadingSession(
    val id: String,
    val articleUrl: String,
    val articleTitle: String,
    val source: String,
    val subject: String,
    val gsPaper: String,
    val startedAtMillis: Long,
    val durationMillis: Long
)

internal data class QuizAttempt(
    val id: String,
    val articleUrl: String,
    val articleTitle: String,
    val subject: String,
    val gsPaper: String,
    val submittedAtMillis: Long,
    val totalQuestions: Int,
    val answeredCount: Int,
    val correctCount: Int
) {
    val wrongCount: Int
        get() = (answeredCount - correctCount).coerceAtLeast(0)
}

internal data class ReadingSessionWindow(
    val startedAtMillis: Long,
    val durationMillis: Long
)

internal class ForegroundReadingSessionClock {
    private var startedAtMillis: Long? = null
    private var startedElapsedRealtime: Long? = null

    fun resume(wallClockMillis: Long, elapsedRealtimeMillis: Long) {
        if (startedElapsedRealtime == null) {
            startedAtMillis = wallClockMillis
            startedElapsedRealtime = elapsedRealtimeMillis
        }
    }

    fun pause(elapsedRealtimeMillis: Long): ReadingSessionWindow? {
        val startWall = startedAtMillis
        val startElapsed = startedElapsedRealtime
        startedAtMillis = null
        startedElapsedRealtime = null
        if (startWall == null || startElapsed == null) return null
        val duration = (elapsedRealtimeMillis - startElapsed).coerceAtLeast(0L)
        if (duration < MIN_SESSION_MILLIS || duration > MAX_SESSION_MILLIS) return null
        return ReadingSessionWindow(startWall, duration)
    }

    companion object {
        const val MIN_SESSION_MILLIS = 1_000L
        const val MAX_SESSION_MILLIS = 24L * 60L * 60L * 1_000L
    }
}

internal data class DailyReadingSummary(
    val date: LocalDate,
    val articleCount: Int,
    val durationMillis: Long
)

internal data class ReadingStatsSummary(
    val uniqueArticleCount: Int,
    val sessionCount: Int,
    val totalDurationMillis: Long,
    val days: List<DailyReadingSummary>
)

internal data class QuizStatsSummary(
    val attemptCount: Int,
    val answersAttempted: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val attempts: List<QuizAttempt>
)

internal object StudyStatsPolicy {
    private val indiaZone = ZoneId.of("Asia/Kolkata")

    private data class MutableDay(
        val articleUrls: MutableSet<String> = mutableSetOf(),
        var durationMillis: Long = 0L
    )

    fun readingSummary(
        sessions: List<ReadingSession>,
        dateRange: StatsDateRange,
        subject: String,
        gsPaper: String,
        today: LocalDate,
        zone: ZoneId = indiaZone
    ): ReadingStatsSummary {
        val eligible = sessions.filter { session ->
            session.articleUrl.isNotBlank() &&
                session.durationMillis in 1L..ForegroundReadingSessionClock.MAX_SESSION_MILLIS &&
                metadataMatches(session.subject, subject) &&
                metadataMatches(session.gsPaper, gsPaper) &&
                overlapsRange(session, dateRange, today, zone)
        }
        val days = linkedMapOf<LocalDate, MutableDay>()
        eligible.forEach { session ->
            val start = session.startedAtMillis
            val endExclusive = start + session.durationMillis
            var cursor = start
            while (cursor < endExclusive) {
                val date = Instant.ofEpochMilli(cursor).atZone(zone).toLocalDate()
                val nextDay = date.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()
                val segmentEnd = minOf(endExclusive, nextDay)
                if (dateRange.includes(date, today) && segmentEnd > cursor) {
                    val day = days.getOrPut(date) { MutableDay() }
                    day.articleUrls.add(session.articleUrl)
                    day.durationMillis += segmentEnd - cursor
                }
                if (segmentEnd <= cursor) break
                cursor = segmentEnd
            }
        }
        val daily = days.entries.sortedByDescending { it.key }.map { (date, value) ->
            DailyReadingSummary(date, value.articleUrls.size, value.durationMillis)
        }
        return ReadingStatsSummary(
            uniqueArticleCount = eligible.map { it.articleUrl }.distinct().size,
            sessionCount = eligible.size,
            totalDurationMillis = daily.sumOf { it.durationMillis },
            days = daily
        )
    }

    fun quizSummary(
        attempts: List<QuizAttempt>,
        dateRange: StatsDateRange,
        subject: String,
        gsPaper: String,
        today: LocalDate,
        zone: ZoneId = indiaZone
    ): QuizStatsSummary {
        val filtered = attempts.filter { attempt ->
            val date = Instant.ofEpochMilli(attempt.submittedAtMillis).atZone(zone).toLocalDate()
            dateRange.includes(date, today) &&
                metadataMatches(attempt.subject, subject) &&
                metadataMatches(attempt.gsPaper, gsPaper)
        }.sortedByDescending { it.submittedAtMillis }
        return QuizStatsSummary(
            attemptCount = filtered.size,
            answersAttempted = filtered.sumOf { it.answeredCount },
            correctAnswers = filtered.sumOf { it.correctCount },
            wrongAnswers = filtered.sumOf { it.wrongCount },
            attempts = filtered
        )
    }

    fun articlesReadOn(
        sessions: List<ReadingSession>,
        date: LocalDate,
        zone: ZoneId = indiaZone
    ): Int = readingSummary(
        sessions, StatsDateRange.TODAY, "All", "All", date, zone
    ).uniqueArticleCount

    fun filterArticles(
        articles: List<Article>,
        dateRange: StatsDateRange,
        subject: String,
        gsPaper: String,
        today: LocalDate,
        zone: ZoneId = indiaZone
    ): List<Article> = articles.filter { article ->
        val relevance = UpscEngine.classify(article)
        val date = ArticleFilters.publishedDate(article, zone)
        val dateMatches = if (dateRange == StatsDateRange.ALL_TIME) {
            date == null || dateRange.includes(date, today)
        } else {
            date != null && dateRange.includes(date, today)
        }
        dateMatches &&
            metadataMatches(relevance.subject, subject) &&
            metadataMatches(relevance.gsPaper, gsPaper)
    }.sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }

    private fun overlapsRange(
        session: ReadingSession,
        dateRange: StatsDateRange,
        today: LocalDate,
        zone: ZoneId
    ): Boolean {
        val startDate = Instant.ofEpochMilli(session.startedAtMillis).atZone(zone).toLocalDate()
        val endDate = Instant.ofEpochMilli(session.startedAtMillis + session.durationMillis - 1L)
            .atZone(zone).toLocalDate()
        if (dateRange == StatsDateRange.ALL_TIME) return !startDate.isAfter(today)
        val firstDate = when (dateRange) {
            StatsDateRange.TODAY -> today
            StatsDateRange.LAST_7_DAYS -> today.minusDays(6)
            StatsDateRange.LAST_30_DAYS -> today.minusDays(29)
            StatsDateRange.ALL_TIME -> LocalDate.MIN
        }
        return !startDate.isAfter(today) && !endDate.isBefore(firstDate)
    }

    private fun metadataMatches(actual: String, selected: String): Boolean =
        selected.equals("All", ignoreCase = true) || actual.equals(selected, ignoreCase = true)
}
