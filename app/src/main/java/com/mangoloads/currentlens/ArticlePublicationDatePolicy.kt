package com.mangoloads.currentlens

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Displays the RSS-supplied article date consistently in the app's India calendar zone. */
internal object ArticlePublicationDatePolicy {
    private val indiaZone = ZoneId.of("Asia/Kolkata")
    private val formatter = DateTimeFormatter
        .ofPattern("d MMM uuuu", Locale.ENGLISH)
        .withZone(indiaZone)

    fun label(publishedAtMillis: Long?): String {
        if (publishedAtMillis == null) return "Date unavailable"
        return runCatching {
            "Date: ${formatter.format(Instant.ofEpochMilli(publishedAtMillis))} (IST)"
        }.getOrDefault("Date unavailable")
    }
}
