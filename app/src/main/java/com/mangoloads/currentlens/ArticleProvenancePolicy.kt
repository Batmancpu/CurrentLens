package com.mangoloads.currentlens

import java.util.Locale

/**
 * Distinguishes publisher-feed metadata from official primary-source evidence.
 * Unknown and legacy provenance is never promoted to a primary-source label.
 */
internal object ArticleProvenancePolicy {
    private val publisherRssSources = setOf(
        "the hindu",
        "indian express",
        "bbc",
        "dw",
        "al jazeera"
    )

    fun label(source: String, feedUrl: String): String {
        if (feedUrl.isBlank()) return "Feed provenance unavailable"
        return if (source.trim().lowercase(Locale.ROOT) in publisherRssSources) {
            "Publisher RSS · secondary source"
        } else {
            "Source type unverified"
        }
    }
}
