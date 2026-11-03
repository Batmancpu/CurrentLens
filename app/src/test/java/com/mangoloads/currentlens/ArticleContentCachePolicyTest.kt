package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleContentCachePolicyTest {
    @Test
    fun retainsMostRecentlyReadEntriesWithinCountAndByteBudgets() {
        val entries = listOf(
            ArticleContentCacheEntry("older", 4, 10),
            ArticleContentCacheEntry("newer", 4, 30),
            ArticleContentCacheEntry("newest", 4, 40)
        )
        val retained = ArticleContentCachePolicy.retain(
            entries, nowMillis = 50, maxAgeMillis = 100,
            maxEntries = 2, maxBytes = 8, maxEntryBytes = 8
        )
        assertEquals(listOf("newest", "newer"), retained)
    }

    @Test
    fun excludesExpiredFutureDatedAndOversizedEntries() {
        val entries = listOf(
            ArticleContentCacheEntry("expired", 2, 1),
            ArticleContentCacheEntry("future", 2, 120),
            ArticleContentCacheEntry("too-large", 12, 90),
            ArticleContentCacheEntry("valid", 3, 90)
        )
        val retained = ArticleContentCachePolicy.retain(
            entries, nowMillis = 100, maxAgeMillis = 50,
            maxEntries = 10, maxBytes = 20, maxEntryBytes = 10
        )
        assertEquals(listOf("valid"), retained)
    }
}
