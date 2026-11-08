package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NewsRepositoryTest {
    @Test
    fun perFeedBudgetAcceptsExactlyTheConfiguredNumberOfEntries() {
        val limit = NewsRepository.MAX_ENTRIES_PER_FEED
        var seen = 0

        repeat(limit) {
            seen = requireNotNull(FeedEntryBudgetPolicy.consume(seen, limit))
        }

        assertEquals(limit, seen)
        assertNull(FeedEntryBudgetPolicy.consume(seen, limit))
    }

    @Test
    fun malformedEntriesConsumeBudgetBeforeA laterValidEntryCanBeParsed() {
        val limit = NewsRepository.MAX_ENTRIES_PER_FEED
        // The parser calls consume at each item/entry start tag, before checking
        // title/link validity. Model a feed with 100 malformed records then a valid one.
        val entryHasUsableArticleLink = List(limit) { false } + true
        var seen = 0
        val acceptedEntryIndexes = mutableListOf<Int>()

        for ((index, isUsable) in entryHasUsableArticleLink.withIndex()) {
            val nextCount = FeedEntryBudgetPolicy.consume(seen, limit) ?: break
            seen = nextCount
            if (isUsable) acceptedEntryIndexes += index
        }

        assertEquals(limit, seen)
        assertTrue(acceptedEntryIndexes.isEmpty())
    }
}
