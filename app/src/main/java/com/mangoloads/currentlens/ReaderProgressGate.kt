package com.mangoloads.currentlens

/**
 * Tracks the end-of-reading signal independently for each article.
 * Reopening or changing font size within the same article must not re-fire it.
 */
internal class ReaderProgressGate {
    private var activeArticle: String? = null
    private var endReached = false

    fun beginArticle(articleKey: String): Boolean {
        if (activeArticle == articleKey) return false
        activeArticle = articleKey
        endReached = false
        return true
    }

    fun markEndReached(): Boolean {
        if (endReached) return false
        endReached = true
        return true
    }
}
