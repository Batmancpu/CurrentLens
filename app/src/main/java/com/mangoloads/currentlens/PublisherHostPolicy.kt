package com.mangoloads.currentlens

import java.net.URI
import java.util.Locale

/**
 * Allows only HTTPS URLs belonging to the publisher identified by the feed.
 * Exact-domain or true subdomain matching is used; look-alike suffixes are rejected.
 */
internal object PublisherHostPolicy {
    private val domains = mapOf(
        "the hindu" to setOf("thehindu.com"),
        "indian express" to setOf("indianexpress.com"),
        "bbc" to setOf("bbc.com", "bbc.co.uk"),
        "dw" to setOf("dw.com"),
        "al jazeera" to setOf("aljazeera.com")
    )

    fun allows(source: String, url: String): Boolean {
        val permitted = domains[source.trim().lowercase(Locale.ROOT)] ?: return false
        val uri = runCatching { URI(url) }.getOrNull() ?: return false
        if (!uri.scheme.equals("https", ignoreCase = true) || uri.userInfo != null) return false
        if (uri.port != -1 && uri.port != 443) return false
        val host = uri.host?.lowercase(Locale.ROOT)?.trimEnd('.') ?: return false
        return permitted.any { domain -> host == domain || host.endsWith(".$domain") }
    }
}


/**
 * Resolves a redirect only when the next request remains inside the same publisher
 * allowlist. Callers must use the returned URL before issuing the next network request.
 */
internal object PublisherRedirectPolicy {
    const val MAX_REDIRECTS = 5

    fun resolveAllowedTarget(
        source: String,
        currentUrl: String,
        location: String,
        redirectsFollowed: Int
    ): String? {
        if (redirectsFollowed < 0 || redirectsFollowed >= MAX_REDIRECTS || location.isBlank()) {
            return null
        }
        if (!PublisherHostPolicy.allows(source, currentUrl)) return null

        val base = runCatching { URI(currentUrl) }.getOrNull() ?: return null
        val reference = runCatching { URI(location.trim()) }.getOrNull() ?: return null
        val target = runCatching { base.resolve(reference).toASCIIString() }.getOrNull() ?: return null
        return target.takeIf { PublisherHostPolicy.allows(source, it) }
    }
}
