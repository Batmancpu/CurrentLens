package com.mangoloads.currentlens

/**
 * Stable key and one-pass relevance index for feed screens that need classification
 * for both filter choices and visible article metadata.
 *
 * The index uses article ID for stable lookup; UpscEngine.classify itself reads only
 * title and description. Publisher, date, image and section changes do not invalidate
 * the relevance result.
 */
internal data class ArticleRelevanceKey(
    val articleId: String,
    val title: String,
    val description: String
)

internal object ArticleRelevanceIndexPolicy {
    fun keys(articles: List<Article>): List<ArticleRelevanceKey> =
        articles.map { ArticleRelevanceKey(it.id, it.title, it.description) }

    fun build(
        articles: List<Article>,
        classify: (Article) -> Relevance = UpscEngine::classify
    ): Map<String, Relevance> {
        val index = LinkedHashMap<String, Relevance>(articles.size)
        articles.forEach { article -> index[article.id] = classify(article) }
        return index
    }
}
