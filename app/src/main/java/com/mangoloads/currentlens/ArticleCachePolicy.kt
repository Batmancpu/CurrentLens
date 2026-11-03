package com.mangoloads.currentlens

/**
 * Retains the newest article metadata within the local cache budget while treating
 * bookmarked stories as pinned entries. The cache may exceed the soft limit only
 * when the user has explicitly saved more stories than that limit.
 */
internal object ArticleCachePolicy {
    fun retain(
        articles: List<Article>,
        bookmarkedUrls: Set<String>,
        cutoffMillis: Long,
        maxArticles: Int
    ): List<Article> {
        val eligible = articles
            .distinctBy { it.url.ifBlank { it.id } }
            .filter { article ->
                val publishedAt = article.publishedAtMillis
                publishedAt == null || publishedAt >= cutoffMillis || article.url in bookmarkedUrls
            }
            .sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }

        val pinned = eligible.filter { it.url in bookmarkedUrls }
        val unpinned = eligible.filterNot { it.url in bookmarkedUrls }
        val remainingSlots = (maxArticles - pinned.size).coerceAtLeast(0)

        return (pinned + unpinned.take(remainingSlots))
            .sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }
    }
}
