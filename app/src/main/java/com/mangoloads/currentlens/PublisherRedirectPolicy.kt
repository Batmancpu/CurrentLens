package com.mangoloads.currentlens

import java.net.URI

/**
 * Restricts RSS endpoint redirects to HTTPS hosts already approved for that publisher.
 * HttpURLConnection follows redirects by default, so feed requests must opt out and
 * consult this policy before opening each subsequent URL.
 */
internal object PublisherRedirectPolicy {
    const val MAX_REDIRECTS = 5
    const val MAX_URL_LENGTH = 4_096

    private val redirectStatusCodes = setOf(301, 302, 303, 307, 308)

    fun isRedirectStatus(status: Int): Boolean = status in redirectStatusCodes

    fun resolveAllowedTarget(
        source: String,
        currentUrl: String,
        location: String,
        redirectsFollowed: Int
    ): String? {
        if (
            redirectsFollowed < 0 ||
            redirectsFollowed >= MAX_REDIRECTS ||
            location.isBlank() ||
            location.length > MAX_URL_LENGTH ||
            !PublisherHostPolicy.allows(source, currentUrl)
        ) {
            return null
        }

        val base = runCatching { URI(currentUrl) }.getOrNull() ?: return null
        val reference = runCatching { URI(location.trim()) }.getOrNull() ?: return null
        val target = runCatching { base.resolve(reference).toASCIIString() }.getOrNull() ?: return null
        if (target.length > MAX_URL_LENGTH) return null
        return target.takeIf { PublisherHostPolicy.allows(source, it) }
    }
}
