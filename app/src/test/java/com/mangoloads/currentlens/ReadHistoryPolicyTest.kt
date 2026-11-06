package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ReadHistoryPolicyTest {
    @Test
    fun addsNewUrlWithoutDroppingExistingMarkers() {
        val existing = listOf("https://example.test/old")
        assertEquals(
            listOf("https://example.test/old", "https://example.test/new"),
            ReadHistoryPolicy.add(existing, "https://example.test/new")
        )
    }

    @Test
    fun duplicateUrlIsIdempotent() {
        val existing = listOf("https://example.test/story")
        assertEquals(existing, ReadHistoryPolicy.add(existing, "https://example.test/story"))
    }

    @Test
    fun blankUrlDoesNotCreateAReadMarker() {
        val existing = listOf("https://example.test/story")
        assertEquals(existing, ReadHistoryPolicy.add(existing, "  "))
    }

    @Test
    fun recentWindowEvictsOnlyTheOldestNewMarker() {
        val existing = listOf("oldest", "middle", "newest")
        assertEquals(
            listOf("middle", "newest", "next"),
            ReadHistoryPolicy.add(existing, "next", maxEntries = 3)
        )
    }

    @Test
    fun retainDeduplicatesAndKeepsInsertionOrderOfNewestEntries() {
        assertEquals(
            listOf("b", "c"),
            ReadHistoryPolicy.retain(listOf("a", "b", "a", "c"), maxEntries = 2)
        )
    }

    @Test
    fun oversizedUrlIsRejectedWithoutChangingHistory() {
        val existing = listOf("https://example.test/story")
        assertEquals(
            existing,
            ReadHistoryPolicy.add(existing, "x".repeat(ReadHistoryPolicy.MAX_URL_LENGTH + 1))
        )
    }

    @Test
    fun zeroCapacityRetainsNoRecentMarkers() {
        assertEquals(emptyList<String>(), ReadHistoryPolicy.add(listOf("old"), "new", maxEntries = 0))
    }
}
