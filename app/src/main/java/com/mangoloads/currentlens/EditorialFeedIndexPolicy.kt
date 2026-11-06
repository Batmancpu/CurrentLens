package com.mangoloads.currentlens

import java.time.LocalDate

internal data class EditorialFeedIndex(
    val eligibleArticles: List<Article>,
    val todayArticles: List<Article>,
    val exploreArticles: List<Article>
)

/** Builds the shared Today/Explore/Search editorial index away from the main thread. */
internal object EditorialFeedIndexPolicy {
    fun build(articles: List<Article>, today: LocalDate): EditorialFeedIndex {
        val eligible = articles.filter(UpscFeedPolicy::shouldInclude)
        return EditorialFeedIndex(
            eligibleArticles = eligible,
            todayArticles = ArticleFilters.today(eligible, today),
            exploreArticles = ArticleFilters.explore(eligible)
        )
    }
}
