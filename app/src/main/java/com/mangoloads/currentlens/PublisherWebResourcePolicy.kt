package com.mangoloads.currentlens

import java.net.URI
import java.util.Locale

/**
 * Restricts direct WebView subresource requests to the article publisher and its
 * known first-party asset hosts. Main-frame navigation is guarded separately.
 *
 * Android WebView may not call shouldInterceptRequest again for redirect hops, so
 * this policy is an initial-request boundary, not a complete redirect firewall.
 */
internal object PublisherWebResourcePolicy {
    // The Hindu's observed image CDN is separate from its article-page domain.
    // Other configured publishers serve their observed assets on domains already
    // covered by PublisherHostPolicy.
    private val additionalAssetDomains = mapOf(
        "the hindu" to setOf("thgim.com")
    )

    fun allows(source: String, url: String): Boolean {
        if (PublisherHostPolicy.allows(source, url)) return true

        val permitted = additionalAssetDomains[source.trim().lowercase(Locale.ROOT)] ?: return false
        if (url.isBlank() || url.length > MAX_URL_LENGTH) return false
        val uri = runCatching { URI(url.trim()) }.getOrNull() ?: return false
        if (!uri.scheme.equals("https", ignoreCase = true) || uri.userInfo != null) return false
        if (uri.port != -1 && uri.port != 443) return false

        val host = uri.host?.lowercase(Locale.ROOT)?.trimEnd('.') ?: return false
        return permitted.any { domain -> host == domain || host.endsWith(".$domain") }
    }

    private const val MAX_URL_LENGTH = 4_096
}
