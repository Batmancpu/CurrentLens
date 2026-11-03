package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DictionaryCachePolicyTest {
    @Test
    fun touchMovesWordToNewestPosition() {
        assertEquals(
            listOf("two", "three", "one"),
            DictionaryCachePolicy.touch(listOf("one", "two", "three"), "one", 3)
        )
    }

    @Test
    fun oldestWordsAreEvictedAtTheConfiguredLimit() {
        assertEquals(
            listOf("b", "c", "d"),
            DictionaryCachePolicy.touch(listOf("a", "b", "c"), "d", 3)
        )
    }

    @Test
    fun cacheHitRefreshesRecencyBeforeEviction() {
        val cache = DictionaryDefinitionCache(maxEntries = 2)
        val first = Definition("first", "", listOf("noun · first meaning"))
        val second = Definition("second", "", listOf("noun · second meaning"))
        val third = Definition("third", "", listOf("noun · third meaning"))

        cache.put("first", first)
        cache.put("second", second)
        assertEquals(first, cache.get("first"))
        cache.put("third", third)

        assertNull(cache.get("second"))
        assertEquals(first, cache.get("first"))
        assertEquals(third, cache.get("third"))
        assertEquals(2, cache.size())
    }

    @Test
    fun successfulNoEntryResultCanBeCachedSeparatelyFromCacheMiss() {
        val cache = DictionaryDefinitionCache(maxEntries = 2)
        val noEntry = Definition("unlistedword", "", emptyList())

        assertNull(cache.get("unlistedword"))
        cache.put("unlistedword", noEntry)

        assertEquals(noEntry, cache.get("unlistedword"))
        assertEquals(emptyList<String>(), cache.get("unlistedword")?.meanings)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsZeroCapacity() {
        DictionaryDefinitionCache(maxEntries = 0)
    }
}
