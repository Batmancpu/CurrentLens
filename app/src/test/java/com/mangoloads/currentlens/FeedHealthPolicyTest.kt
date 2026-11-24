package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

class FeedHealthPolicyTest {
    private val source = FeedSource("BBC", "World", "https://feeds.bbci.co.uk/news/world/rss.xml")
    private val now = java.time.Instant.parse("2026-11-24T10:00:00Z").toEpochMilli()
    private fun story(date: String) = Article("a", "A", "", "https://example.test/a?secret=private",
        "BBC", "World", date)

    @Test fun separatesEmptyAndInvalidDatesFromNetworkFailures() {
        assertEquals(FeedHealthStatus.EMPTY, FeedHealthPolicy.report(source, FeedHealthStatus.OK, emptyList(), now).status)
        val undated = FeedHealthPolicy.report(source, FeedHealthStatus.OK, listOf(story("")), now)
        assertEquals(FeedHealthStatus.NO_DATED_STORIES, undated.status)
        assertFalse(undated.status.isFailure)
        val failure = FeedHealthPolicy.report(source, FeedHealthStatus.TIMEOUT, emptyList(), now)
        assertTrue(failure.status.isFailure)
    }

    @Test fun copyReportOmitsArticleUrlsAndText() {
        val report = FeedHealthPolicy.report(source, FeedHealthStatus.OK,
            listOf(story("2026-11-24T09:00:00Z")), now)
        assertEquals(1, report.datedCount)
        val text = FeedHealthPolicy.diagnosticText(listOf(report))
        assertTrue(text.contains("BBC / World"))
        assertFalse(text.contains("example.test"))
        assertFalse(text.contains("secret"))
    }
}
