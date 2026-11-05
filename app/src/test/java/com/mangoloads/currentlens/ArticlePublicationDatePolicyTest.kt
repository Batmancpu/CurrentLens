package com.mangoloads.currentlens

import java.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticlePublicationDatePolicyTest {
    @Test
    fun rendersFeedTimestampInIndiaCalendarDate() {
        val timestamp = Instant.parse("2026-11-04T20:00:00Z").toEpochMilli()
        assertEquals("Date: 5 Nov 2026 (IST)", ArticlePublicationDatePolicy.label(timestamp))
    }

    @Test
    fun preservesPreviousIndiaDateImmediatelyBeforeMidnight() {
        val timestamp = Instant.parse("2026-11-04T18:29:59.999Z").toEpochMilli()
        assertEquals("Date: 4 Nov 2026 (IST)", ArticlePublicationDatePolicy.label(timestamp))
    }

    @Test
    fun neverInventsDateWhenFeedTimestampIsMissing() {
        assertEquals("Date unavailable", ArticlePublicationDatePolicy.label(null))
    }
}
