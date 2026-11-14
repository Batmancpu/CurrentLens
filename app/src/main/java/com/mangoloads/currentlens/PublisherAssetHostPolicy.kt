package com.mangoloads.currentlens

import java.net.URI
import java.util.Locale

/**
 * Limits RSS-provided image requests to the publisher's own domains and documented
 * publisher CDNs. This is intentionally separate from PublisherHostPolicy: a CDN may
 * serve images without being an allowed article-page or redirect destination.
 */
internal object PublisherAssetHostPolicy {
    private val domains = mapOf(
        "the hindu" to setOf("thehindu.com", "thgim.com"),
        "indian express" to setOf("indianexpress.com"),
        "bbc" to setOf("bbc.com", "bbc.co.uk", "bbci.co.uk"),
        "dw" to setOf("dw.com"),
        "al jazeera" to setOf("aljazeera.com")
    )

    fun allows(source: String, url: String): Boolean {
        if (url.isBlank() || url.length > MAX_URL_LENGTH) return false
        val permitted = domains[source.trim().lowercase(Locale.ROOT)] ?: return false
        val uri = runCatching { URI(url.trim()) }.getOrNull() ?: return false
        if (!uri.scheme.equals("https", ignoreCase = true) || uri.userInfo != null) return false
        if (uri.port != -1 && uri.port != 443) return false
        val host = uri.host?.lowercase(Locale.ROOT)?.trimEnd('.') ?: return false
        return permitted.any { domain -> host == domain || host.endsWith(".$domain") }
    }

    private const val MAX_URL_LENGTH = 4_096
}

internal enum class FeedImageElement {
    ENCLOSURE,
    THUMBNAIL,
    CONTENT
}

internal data class FeedImageCandidate(
    val url: String,
    val element: FeedImageElement,
    val type: String = "",
    val medium: String = ""
)

/**
 * Media RSS content can describe images, video or audio. Only image candidates from
 * the source publisher's HTTPS asset domains are eligible for card thumbnails.
 */
internal object FeedImagePolicy {
    private val imageExtension = Regex("\\.(?:avif|gif|jpe?g|png|webp)$", RegexOption.IGNORE_CASE)

    fun select(source: String, candidates: List<FeedImageCandidate>): String? =
        candidates.lastOrNull { candidate ->
            isImage(candidate) && PublisherAssetHostPolicy.allows(source, candidate.url)
        }?.url?.trim()

    private fun isImage(candidate: FeedImageCandidate): Boolean {
        val medium = candidate.medium.trim().lowercase(Locale.ROOT)
        val type = candidate.type.trim().substringBefore(';').lowercase(Locale.ROOT)
        if (medium.isNotEmpty() && medium != "image") return false
        if (type.isNotEmpty() && !type.startsWith("image/")) return false

        val declaredImage = medium == "image" || type.startsWith("image/")
        val hasImageExtension = runCatching {
            URI(candidate.url.trim()).path?.let(imageExtension::containsMatchIn) == true
        }.getOrDefault(false)

        return when (candidate.element) {
            FeedImageElement.THUMBNAIL -> true
            FeedImageElement.ENCLOSURE, FeedImageElement.CONTENT -> declaredImage || hasImageExtension
        }
    }
}
