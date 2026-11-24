package com.mangoloads.currentlens

internal data class ReviewItem(val article: Article, val relevance: Relevance, val lastPracticeMillis: Long?)

internal object ReviewPolicy {
    const val MAX_NOTE_CHARS = 2000
    fun normalizeNote(text: String): String = text.take(MAX_NOTE_CHARS).trim()

    /** Saved articles only. Never practised first, then the least recently practised. */
    fun queue(articles: List<Article>, bookmarks: Set<String>, attempts: List<QuizAttempt>): List<ReviewItem> {
        val latest = attempts.groupBy { it.articleUrl }.mapValues { (_, values) ->
            values.maxOf { it.submittedAtMillis }
        }
        return articles.asSequence()
            .filter { it.url in bookmarks }
            .distinctBy { it.url }
            .map { ReviewItem(it, UpscEngine.classify(it), latest[it.url]) }
            .sortedWith(compareBy<ReviewItem> { it.lastPracticeMillis ?: Long.MIN_VALUE }
                .thenByDescending { it.article.publishedAtMillis ?: Long.MIN_VALUE })
            .toList()
    }
}
