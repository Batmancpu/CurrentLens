package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BookmarkSetPolicyTest {
    @Test
    fun addingBookmarkPreservesEveryExistingSavedUrl() {
        val existing = setOf("https://example.test/a", "https://example.test/b")
        val result = BookmarkSetPolicy.toggle(existing, "https://example.test/c")

        assertTrue(result.added)
        assertTrue(result.changed)
        assertEquals(existing + "https://example.test/c", result.savedUrls)
    }

    @Test
    fun removingBookmarkChangesOnlyTheSelectedUrl() {
        val existing = setOf("https://example.test/a", "https://example.test/b")
        val result = BookmarkSetPolicy.toggle(existing, "https://example.test/a")

        assertFalse(result.added)
        assertTrue(result.changed)
        assertEquals(setOf("https://example.test/b"), result.savedUrls)
    }

    @Test
    fun blankUrlDoesNotMutateSavedBookmarks() {
        val existing = setOf("https://example.test/a")
        val result = BookmarkSetPolicy.toggle(existing, "  ")

        assertFalse(result.added)
        assertFalse(result.changed)
        assertEquals(existing, result.savedUrls)
    }

    @Test
    fun repeatedToggleIsReversibleWithoutDroppingUnrelatedBookmarks() {
        val existing = setOf("https://example.test/keep")
        val added = BookmarkSetPolicy.toggle(existing, "https://example.test/new")
        val removed = BookmarkSetPolicy.toggle(added.savedUrls, "https://example.test/new")

        assertEquals(existing, removed.savedUrls)
        assertFalse(removed.added)
    }
}
