package com.mangoloads.currentlens

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Calendar-day clock used by the Today feed; publisher timestamps remain unchanged. */
internal object AsiaKolkataDayPolicy {
    private val zone = ZoneId.of("Asia/Kolkata")

    fun today(now: Instant = Instant.now()): LocalDate =
        now.atZone(zone).toLocalDate()

    fun millisUntilNextMidnight(now: Instant): Long {
        val nextMidnight = now.atZone(zone)
            .toLocalDate()
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
        return Duration.between(now, nextMidnight).toMillis().coerceAtLeast(1L)
    }
}
