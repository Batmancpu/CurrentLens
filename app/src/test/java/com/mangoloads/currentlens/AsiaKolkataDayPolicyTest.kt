package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate

class AsiaKolkataDayPolicyTest {
    @Test
    fun calendarDateChangesAtAsiaKolkataMidnightNotUtcMidnight() {
        val oneSecondBeforeKolkataMidnight = Instant.parse("2026-11-07T18:29:59Z")
        val atKolkataMidnight = Instant.parse("2026-11-07T18:30:00Z")

        assertEquals(
            LocalDate.of(2026, 11, 7),
            AsiaKolkataDayPolicy.today(oneSecondBeforeKolkataMidnight)
        )
        assertEquals(
            LocalDate.of(2026, 11, 8),
            AsiaKolkataDayPolicy.today(atKolkataMidnight)
        )
    }

    @Test
    fun foregroundRefreshWaitEndsAtTheNextKolkataMidnight() {
        val oneSecondBeforeKolkataMidnight = Instant.parse("2026-11-07T18:29:59Z")
        val atKolkataMidnight = Instant.parse("2026-11-07T18:30:00Z")

        assertEquals(
            1_000L,
            AsiaKolkataDayPolicy.millisUntilNextMidnight(oneSecondBeforeKolkataMidnight)
        )
        assertEquals(
            86_400_000L,
            AsiaKolkataDayPolicy.millisUntilNextMidnight(atKolkataMidnight)
        )
    }
}
