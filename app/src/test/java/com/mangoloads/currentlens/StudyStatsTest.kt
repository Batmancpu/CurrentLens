package com.mangoloads.currentlens

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class StudyStatsTest {
    private val zone = ZoneId.of("Asia/Kolkata")
    private val today = LocalDate.of(2026, 11, 3)

    private fun epoch(date: LocalDate, hour: Int = 12, minute: Int = 0): Long =
        ZonedDateTime.of(date.year, date.monthValue, date.dayOfMonth, hour, minute, 0, 0, zone)
            .toInstant().toEpochMilli()

    private fun session(
        id: String,
        url: String,
        date: LocalDate,
        duration: Long,
        subject: String = "Polity & Governance",
        gs: String = "GS II"
    ) = ReadingSession(id, url, id, "The Hindu", subject, gs, epoch(date), duration)

    @Test
    fun foregroundClockIsIdempotentAndUsesElapsedTime() {
        val clock = ForegroundReadingSessionClock()
        clock.resume(10_000L, 500L)
        clock.resume(20_000L, 900L)
        val window = clock.pause(8_500L)
        assertNotNull(window)
        assertEquals(10_000L, window!!.startedAtMillis)
        assertEquals(8_000L, window.durationMillis)
        assertNull(clock.pause(9_000L))
    }

    @Test
    fun foregroundClockDiscardsAccidentalTapSessions() {
        val clock = ForegroundReadingSessionClock()
        clock.resume(10_000L, 500L)
        assertNull(clock.pause(1_200L))
    }

    @Test
    fun readingTimeIsSplitAcrossIndiaLocalMidnight() {
        val start = ZonedDateTime.of(2026, 11, 2, 23, 59, 0, 0, zone).toInstant().toEpochMilli()
        val record = ReadingSession("one", "https://example.com/a", "Article", "The Hindu",
            "Polity & Governance", "GS II", start, 120_000L)
        val summary = StudyStatsPolicy.readingSummary(
            listOf(record), StatsDateRange.LAST_7_DAYS, "All", "All", today, zone
        )
        assertEquals(1, summary.uniqueArticleCount)
        assertEquals(120_000L, summary.totalDurationMillis)
        assertEquals(2, summary.days.size)
        assertEquals(LocalDate.of(2026, 11, 3), summary.days[0].date)
        assertEquals(60_000L, summary.days[0].durationMillis)
        assertEquals(LocalDate.of(2026, 11, 2), summary.days[1].date)
        assertEquals(60_000L, summary.days[1].durationMillis)
    }

    @Test
    fun readingFiltersApplyDateSubjectAndGsPaper() {
        val records = listOf(
            session("a", "url-a", today, 60_000L),
            session("b", "url-b", today.minusDays(9), 120_000L, "Indian Economy", "GS III"),
            session("c", "url-c", today, 30_000L, "Indian Economy", "GS III")
        )
        val summary = StudyStatsPolicy.readingSummary(
            records, StatsDateRange.LAST_7_DAYS, "Indian Economy", "GS III", today, zone
        )
        assertEquals(1, summary.uniqueArticleCount)
        assertEquals(30_000L, summary.totalDurationMillis)
        assertEquals(1, summary.sessionCount)
    }

    @Test
    fun dailyReadCountUsesOnlyDatedReadingSessions() {
        val records = listOf(
            session("a", "same-url", today, 60_000L),
            session("b", "same-url", today, 90_000L),
            session("c", "other-url", today.minusDays(1), 30_000L)
        )
        assertEquals(1, StudyStatsPolicy.articlesReadOn(records, today, zone))
    }

    @Test
    fun quizSummaryCountsOnlySubmittedAnswersAndHonoursFilters() {
        val records = listOf(
            QuizAttempt("a", "url-a", "A", "Polity", "GS II", epoch(today), 5, 5, 3),
            QuizAttempt("b", "url-b", "B", "Economy", "GS III", epoch(today.minusDays(10)), 5, 4, 2),
            QuizAttempt("c", "url-c", "C", "Polity", "GS II", epoch(today), 5, 2, 1)
        )
        val summary = StudyStatsPolicy.quizSummary(
            records, StatsDateRange.TODAY, "Polity", "GS II", today, zone
        )
        assertEquals(2, summary.attemptCount)
        assertEquals(7, summary.answersAttempted)
        assertEquals(4, summary.correctAnswers)
        assertEquals(3, summary.wrongAnswers)
    }

    @Test
    fun savedArticleFiltersUsePublicationDateAndIndicativeSyllabusMapping() {
        val articles = listOf(
            Article("1", "Parliament passes bill", "", "url-1", "The Hindu", "India",
                "Tue, 03 Nov 2026 06:00:00 GMT"),
            Article("2", "RBI changes policy rate", "", "url-2", "The Hindu", "Economy",
                "Sat, 24 Oct 2026 06:00:00 GMT")
        )
        val filtered = StudyStatsPolicy.filterArticles(
            articles, StatsDateRange.TODAY, "Constitution", "GS II", today, zone
        )
        assertEquals(listOf("1"), filtered.map { it.id })
    }
}
