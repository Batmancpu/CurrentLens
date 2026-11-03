package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryCachePolicyTest {
    @Test
    fun touchMovesWordToNewestPosition() {
        assertEquals(listOf("two", "three", "one"), DictionaryCachePolicy.touch(listOf("one", "two", "three"), "one", 3))
    }

    @Test
    fun oldestWordsAreEvictedAtTheConfiguredLimit() {
        assertEquals(listOf("b", "c", "d"), DictionaryCachePolicy.touch(listOf("a", "b", "c"), "d", 3))
    }
}
