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
    val type: String = "",
    /** Effective XML Base for this link, when the feed declares xml:base. */
    val baseUrl: String? = null
)

internal object FeedLinkPolicy {
    fun select(candidates: List<FeedLinkCandidate>, baseUrl: String? = null): String? {
        val alternates = candidates.mapNotNull { candidate ->
            val href = resolveHttpReference(candidate.href, candidate.baseUrl ?: baseUrl)
                ?: return@mapNotNull null
            if (isAlternate(candidate.rel)) {
                candidate.copy(href = href)
            } else {
                null
            }
        }

        return alternates.firstOrNull { isHtml(it.type) }?.href
            ?: alternates.firstOrNull { it.type.isBlank() }?.href
            ?: alternates.firstOrNull { !isFeedType(it.type) }?.href
    }

    /**
     * Resolves an RSS/Atom IRI reference against its effective document base.
     * Relative Atom href values are valid; only the resolved HTTP(S) destination
     * is accepted. Fragment-only references cannot identify a separate article.
     */
    internal fun resolveHttpReference(reference: String, baseUrl: String?): String? {
        val raw = reference.trim()
        if (raw.isBlank()) return null
        val referenceUri = runCatching { URI(raw) }.getOrNull() ?: return null
        if (!referenceUri.isAbsolute &&
            referenceUri.rawPath.isNullOrEmpty() &&
            referenceUri.rawQuery.isNullOrEmpty()
        ) {
            return null
        }

        val resolved = if (referenceUri.isAbsolute) {
            referenceUri
        } else {
            val base = baseUrl?.trim()?.let { runCatching { URI(it) }.getOrNull() }
                ?: return null
            if (!isHttpUri(base)) return null
            runCatching { base.resolve(referenceUri) }.getOrNull() ?: return null
        }

        return resolved.takeIf(::isHttpUri)?.toASCIIString()
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

    private fun isHttpUri(uri: URI): Boolean =
        (uri.scheme.equals("https", ignoreCase = true) ||
            uri.scheme.equals("http", ignoreCase = true)) &&
            !uri.host.isNullOrBlank() &&
            uri.rawUserInfo == null
}
