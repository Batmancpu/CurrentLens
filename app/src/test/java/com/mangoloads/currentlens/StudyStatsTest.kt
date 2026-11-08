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
    fun foregroundClockRequiresFiveSecondsOfForegroundReading() {
        val shortSession = ForegroundReadingSessionClock()
        shortSession.resume(10_000L, 100L)
        assertNull(shortSession.pause(5_099L))

        val validSession = ForegroundReadingSessionClock()
        validSession.resume(20_000L, 200L)
        val window = validSession.pause(5_200L)
        assertNotNull(window)
        assertEquals(5_000L, window!!.durationMillis)
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
    fun legacyReadMarkersCountAsUniqueStatusButNeverCreateTimedHistory() {
        val records = listOf(session("new", "shared-url", today, 60_000L))
        val legacyMarkers = setOf("legacy-url", "shared-url", "")

        assertEquals(2, StudyStatsPolicy.totalUniqueReadCount(legacyMarkers, records))
        val summary = StudyStatsPolicy.readingSummary(
            emptyList(), StatsDateRange.ALL_TIME, "All", "All", today, zone
        )
        assertEquals(0, summary.uniqueArticleCount)
        assertEquals(0L, summary.totalDurationMillis)
    }

    @Test
    fun submittedAnswerSnapshotRetainsSelectedAndCorrectAnswersWithProvenance() {
        val questions = listOf(
            QuizQuestion(
                "Basic", "Comprehension", "Which option is correct?",
                listOf("Wrong option", "Correct option"), 1,
                "The second option is supported.", "Original practice question."
            ),
            QuizQuestion(
                "Application", "Analysis", "Which source should be checked?",
                listOf("Official Gazette", "Social post"), 0,
                "Use the primary document.", "UPSC-style practice; not an official PYQ."
            )
        )

        val snapshot = QuizAttemptPolicy.snapshot(
            selectedAnswers = mapOf(1 to 0, 0 to 0, 9 to 1),
            questions = questions
        )

        assertEquals(2, snapshot.size)
        assertEquals(0, snapshot[0].questionIndex)
        assertEquals("Which option is correct?", snapshot[0].prompt)
        assertEquals("Wrong option", snapshot[0].selectedAnswer)
        assertEquals("Correct option", snapshot[0].correctAnswer)
        assertEquals(false, snapshot[0].isCorrect)
        assertEquals("Original practice question.", snapshot[0].provenance)
        assertEquals(true, snapshot[1].isCorrect)
    }

    @Test
    fun quizAttemptRetentionIsBoundedAndKeepsNewestSubmissions() {
        val older = QuizAttempt("old", "old-url", "Old", "Polity", "GS II", 100L, 5, 1, 1)
        val newer = QuizAttempt("new", "new-url", "New", "Economy", "GS III", 200L, 5, 1, 0)

        assertEquals(listOf(newer), QuizAttemptPolicy.retain(listOf(older, newer), 1))
        assertEquals(emptyList<QuizAttempt>(), QuizAttemptPolicy.retain(listOf(older), 0))
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

    @Test
    fun savedArticleFiltersReuseSuppliedRelevanceIndex() {
        val article = Article(
            "cached", "Parliament passes bill", "", "url-cached", "The Hindu", "India",
            "Tue, 03 Nov 2026 06:00:00 GMT"
        )
        // Deliberately differs from the headline's normal Constitution/GS II mapping:
        // the supplied index must be the source used by the saved-filter pass.
        val cached = Relevance(
            gsPaper = "GS III",
            subject = "Indian Economy",
            syllabusLink = "Cached relevance for this feed snapshot.",
            matchedTerms = listOf("repo rate"),
            signalLabel = "1 matched keyword",
            topicKey = "economy"
        )
        val filtered = StudyStatsPolicy.filterArticles(
            listOf(article),
            StatsDateRange.TODAY,
            "Indian Economy",
            "GS III",
            today,
            zone,
            relevanceByArticleId = mapOf(article.id to cached)
        )
        assertEquals(listOf(article.id), filtered.map { it.id })
    }
}
