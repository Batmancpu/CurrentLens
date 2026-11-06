package com.mangoloads.currentlens

/**
 * Retains the newest dated article metadata within the local cache budget while treating
 * bookmarked stories as pinned entries. Undated and materially future-dated feed items
 * cannot be placed reliably on the Today/Explore timeline, so they are excluded unless
 * the user explicitly saves them.
 */
internal object ArticleCachePolicy {
    private const val FUTURE_DATE_TOLERANCE_MILLIS = 5L * 60L * 1000L

    fun retain(
        articles: List<Article>,
        bookmarkedUrls: Set<String>,
        cutoffMillis: Long,
        maxArticles: Int,
        nowMillis: Long = System.currentTimeMillis()
    ): List<Article> {
        val latestAllowedMillis = nowMillis + FUTURE_DATE_TOLERANCE_MILLIS
        val eligible = articles
            .distinctBy { it.url.ifBlank { it.id } }
            .filter { article ->
                if (article.url in bookmarkedUrls) {
                    true
                } else {
                    article.publishedAtMillis?.let { publishedAt ->
                        publishedAt >= cutoffMillis && publishedAt <= latestAllowedMillis
                    } == true
                }
            }
            .sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }

        val pinned = eligible.filter { it.url in bookmarkedUrls }
        val unpinned = eligible.filterNot { it.url in bookmarkedUrls }
        val remainingSlots = (maxArticles - pinned.size).coerceAtLeast(0)

        return (pinned + unpinned.take(remainingSlots))
            .sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }
    }
}
