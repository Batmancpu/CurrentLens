package com.mangoloads.currentlens

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Parses publisher RSS timestamps without silently assigning UTC to timezone-less values.
 * A missing offset is interpreted in the app's India calendar zone so Today and date filters
 * do not shift late-evening Indian publication times into the following day.
 */
internal object FeedDateParser {
    private val indiaZone = ZoneId.of("Asia/Kolkata")
    private val compactOffset = Regex("([+-]\\d{2})(\\d{2})$")

    fun parse(value: String): Long? {
        val raw = value.trim()
        if (raw.isBlank()) return null
        val normalized = raw.replace(compactOffset) { match ->
            match.groupValues[1] + ":" + match.groupValues[2]
        }

        return runCatching {
            ZonedDateTime.parse(raw, DateTimeFormatter.RFC_1123_DATE_TIME)
                .toInstant().toEpochMilli()
        }.getOrNull()
            ?: runCatching {
                OffsetDateTime.parse(normalized, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    .toInstant().toEpochMilli()
            }.getOrNull()
            ?: runCatching { Instant.parse(normalized).toEpochMilli() }.getOrNull()
            ?: runCatching {
                LocalDateTime.parse(normalized, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .atZone(indiaZone).toInstant().toEpochMilli()
            }.getOrNull()
            ?: runCatching {
                LocalDate.parse(normalized, DateTimeFormatter.ISO_LOCAL_DATE)
                    .atStartOfDay(indiaZone).toInstant().toEpochMilli()
            }.getOrNull()
    }
}
