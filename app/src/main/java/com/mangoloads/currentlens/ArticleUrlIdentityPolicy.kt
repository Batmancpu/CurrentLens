package com.mangoloads.currentlens

import java.net.URI
import java.util.Locale

/**
 * Builds a comparison key for publisher article URLs without changing the URL used by the reader.
 *
 * Only well-known campaign/click identifiers are removed. Publisher query parameters that may
 * select content (for example, article IDs, editions or output formats) are preserved.
 */
internal object ArticleUrlIdentityPolicy {
    private val trackingKeys = setOf(
        "fbclid",
        "gclid",
        "dclid",
        "msclkid",
        "gbraid",
        "wbraid",
        "mc_cid",
        "mc_eid",
        "igshid",
        "twclid",
        "li_fat_id"
    )

    fun key(value: String): String {
        val raw = value.trim()
        if (raw.isBlank()) return ""

        val uri = runCatching { URI(raw) }.getOrNull() ?: return raw
        val scheme = uri.scheme?.lowercase(Locale.ROOT) ?: return raw
        val host = uri.host?.lowercase(Locale.ROOT)?.trimEnd('.') ?: return raw
        if (scheme !in setOf("http", "https") || host.isBlank() || uri.rawUserInfo != null) {
            return raw
        }

        val port = when {
            scheme == "https" && uri.port == 443 -> ""
            scheme == "http" && uri.port == 80 -> ""
            uri.port >= 0 -> ":${uri.port}"
            else -> ""
        }
        val path = uri.normalize().rawPath.orEmpty().ifBlank { "/" }
        val query = uri.rawQuery
            ?.split('&')
            ?.filter { part ->
                part.isNotBlank() && !isTrackingParameter(part.substringBefore('='))
            }
            ?.joinToString("&")
            ?.takeIf { it.isNotBlank() }

        return buildString {
            append(scheme)
            append("://")
            append(host)
            append(port)
            append(path)
            if (query != null) {
                append('?')
                append(query)
            }
        }
    }

    /**
     * Deduplicates URL variants while retaining the exact publisher URL for navigation.
     * Explicitly saved variants win over newer incoming tracking variants; otherwise the
     * newest publication date wins. Original order breaks ties, preserving section-feed
     * provenance when section feeds are fetched before broad feeds.
     */
    fun deduplicate(
        articles: List<Article>,
        bookmarkedUrls: Set<String>
    ): List<Article> {
        val bookmarkedKeys = bookmarkedUrls.mapTo(HashSet(), ::key)

        data class Candidate(
            val article: Article,
            val identity: String,
            val bookmarked: Boolean,
            val publishedAt: Long,
            val originalIndex: Int
        )

        return articles.mapIndexed { index, article ->
            val urlKey = key(article.url)
            Candidate(
                article = article,
                identity = urlKey.ifBlank { article.id.ifBlank { article.title } },
                bookmarked = urlKey.isNotBlank() && urlKey in bookmarkedKeys,
                publishedAt = article.publishedAtMillis ?: Long.MIN_VALUE,
                originalIndex = index
            )
        }
            .sortedWith(
                compareBy<Candidate> { if (it.bookmarked) 0 else 1 }
                    .thenByDescending { it.publishedAt }
                    .thenBy { it.originalIndex }
            )
            .distinctBy { it.identity }
            .map { it.article }
    }

    private fun isTrackingParameter(name: String): Boolean {
        val normalized = name.lowercase(Locale.ROOT)
        return normalized.startsWith("utm_") || normalized in trackingKeys
    }
}
