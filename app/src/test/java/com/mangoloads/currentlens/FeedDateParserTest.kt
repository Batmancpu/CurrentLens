package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class FeedDateParserTest {
    private val indiaZone = ZoneId.of("Asia/Kolkata")

    @Test
    fun preservesExplicitRfc1123Offset() {
        assertEquals(
            Instant.parse("2026-11-03T04:30:00Z").toEpochMilli(),
            FeedDateParser.parse("Tue, 03 Nov 2026 10:00:00 +0530")
        )
    }

    @Test
    fun preservesExplicitIsoOffset() {
        assertEquals(
            Instant.parse("2026-11-03T04:30:00Z").toEpochMilli(),
            FeedDateParser.parse("2026-11-03T10:00:00+05:30")
        )
    }

    @Test
    fun timezoneLessIsoDateTimeUsesIndiaZoneInsteadOfUtc() {
        val expected = LocalDateTime.of(2026, 11, 3, 23, 45)
            .atZone(indiaZone).toInstant().toEpochMilli()

        assertEquals(expected, FeedDateParser.parse("2026-11-03T23:45:00"))
    }

    @Test
    fun dateOnlyValueUsesStartOfIndiaCalendarDay() {
        val expected = LocalDate.of(2026, 11, 3)
            .atStartOfDay(indiaZone).toInstant().toEpochMilli()

        assertEquals(expected, FeedDateParser.parse("2026-11-03"))
    }

    @Test
    fun malformedAndEmptyValuesRemainUndated() {
        assertNull(FeedDateParser.parse(""))
        assertNull(FeedDateParser.parse("not a publisher date"))
    }
}
