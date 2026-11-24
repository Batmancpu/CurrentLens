package com.mangoloads.currentlens

/** Preserve chronological order within each group; prioritize eligible stories only under pressure. */
internal object EditorialBudgetPolicy {
    fun prioritize(
        ordered: List<Article>,
        limit: Int,
        isEligible: (Article) -> Boolean = UpscFeedPolicy::shouldInclude
    ): List<Article> {
        if (limit <= 0) return emptyList()
        if (ordered.size <= limit) return ordered
        val eligible = ArrayList<Article>(limit)
        val other = ArrayList<Article>(limit)
        for (article in ordered) {
            if (isEligible(article)) {
                eligible.add(article)
                if (eligible.size == limit) return eligible
            } else if (other.size < limit) {
                other.add(article)
            }
        }
        return eligible + other.take(limit - eligible.size)
    }
}
