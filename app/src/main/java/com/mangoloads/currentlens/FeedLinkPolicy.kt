package com.mangoloads.currentlens

import java.net.URI
import java.util.Locale

/**
 * Selects an article destination from RSS/Atom link elements.
 *
 * Atom entries may contain both a feed-level rel="self" link and an article
 * rel="alternate" link. Their document order is not significant, so selection
 * must be based on relation and media type rather than the last href parsed.
 */
internal data class FeedLinkCandidate(
    val href: String,
    val rel: String = "",
    val type: String = ""
)

internal object FeedLinkPolicy {
    fun select(candidates: List<FeedLinkCandidate>): String? {
        val alternates = candidates.mapNotNull { candidate ->
            val href = candidate.href.trim()
            if (isHttpUrl(href) && isAlternate(candidate.rel)) {
                candidate.copy(href = href)
            } else {
                null
            }
        }

        return alternates.firstOrNull { isHtml(it.type) }?.href
            ?: alternates.firstOrNull { it.type.isBlank() }?.href
            ?: alternates.firstOrNull { !isFeedType(it.type) }?.href
    }

    private fun isAlternate(rel: String): Boolean {
        val value = rel.trim()
        if (value.isBlank() || value.equals("alternate", ignoreCase = true)) return true
        return value.substringAfterLast('/').equals("alternate", ignoreCase = true)
    }

    private fun isHtml(type: String): Boolean {
        val mediaType = type.substringBefore(';').trim().lowercase(Locale.ROOT)
        return mediaType == "text/html" || mediaType == "application/xhtml+xml"
    }

    private fun isFeedType(type: String): Boolean {
        val mediaType = type.substringBefore(';').trim().lowercase(Locale.ROOT)
        return mediaType == "application/atom+xml" ||
            mediaType == "application/rss+xml" ||
            mediaType == "application/rdf+xml"
    }

    private fun isHttpUrl(value: String): Boolean = runCatching {
        val uri = URI(value)
        (uri.scheme.equals("https", ignoreCase = true) ||
            uri.scheme.equals("http", ignoreCase = true)) &&
            !uri.host.isNullOrBlank()
    }.getOrDefault(false)
}
