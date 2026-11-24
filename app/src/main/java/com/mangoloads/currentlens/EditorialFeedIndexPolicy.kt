package com.mangoloads.currentlens

import java.time.LocalDate

internal data class EditorialFeedIndex(
    val eligibleArticles: List<Article>,
    val todayArticles: List<Article>,
    val exploreArticles: List<Article>,
    val relevanceById: Map<String, Relevance> = emptyMap()
)

/** Builds the shared Today/Explore/Search editorial index away from the main thread. */
internal object EditorialFeedIndexPolicy {
    fun build(articles: List<Article>, today: LocalDate): EditorialFeedIndex {
        val classified = articles.mapNotNull { article ->
            UpscFeedPolicy.eligibleRelevance(article)?.let { article to it }
        }
        val eligible = classified.map { it.first }
        return EditorialFeedIndex(
            eligibleArticles = eligible,
            todayArticles = ArticleFilters.today(eligible, today),
            exploreArticles = ArticleFilters.explore(eligible),
            relevanceById = classified.associate { (article, relevance) -> article.id to relevance }
        )
    }
}
