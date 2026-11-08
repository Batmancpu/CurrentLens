package com.mangoloads.currentlens

/**
 * Fails closed on RSS article destinations that the in-app reader cannot safely open.
 * Feed endpoints are trusted publisher feeds, but individual item links remain untrusted
 * input and must belong to the same configured HTTPS publisher.
 */
internal object FeedArticleUrlPolicy {
    const val MAX_URL_LENGTH = 4_096

    fun select(source: String, candidate: String?): String? {
        val url = candidate
            ?.trim()
            ?.takeIf { it.isNotEmpty() && it.length <= MAX_URL_LENGTH }
            ?: return null

        return url.takeIf { PublisherHostPolicy.allows(source, it) }
    }
}
