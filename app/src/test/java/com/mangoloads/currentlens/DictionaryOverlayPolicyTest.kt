package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionaryOverlayPolicyTest {
    @Test
    fun collapsedCardShowsOnlyTheFirstMeaningAndNoRelatedWords() {
        assertEquals(1, DictionaryOverlayPolicy.visibleMeanings(5, expanded = false))
        assertEquals(0, DictionaryOverlayPolicy.visibleSynonyms(8, expanded = false))
    }

    @Test
    fun expandedCardCapsMeaningsAndRelatedWordsWithoutInventingContent() {
        assertEquals(5, DictionaryOverlayPolicy.visibleMeanings(9, expanded = true))
        assertEquals(3, DictionaryOverlayPolicy.visibleMeanings(3, expanded = true))
        assertEquals(8, DictionaryOverlayPolicy.visibleSynonyms(12, expanded = true))
        assertEquals(2, DictionaryOverlayPolicy.visibleSynonyms(2, expanded = true))
        assertEquals(0, DictionaryOverlayPolicy.visibleMeanings(-1, expanded = true))
    }

    @Test
    fun upwardSwipeExpandsOnlyAfterTheGestureThreshold() {
        assertFalse(DictionaryOverlayPolicy.shouldExpand(-47f, 48f))
        assertTrue(DictionaryOverlayPolicy.shouldExpand(-48f, 48f))
        assertFalse(DictionaryOverlayPolicy.shouldExpand(Float.NaN, 48f))
    }

    @Test
    fun downwardSwipeCollapsesOnlyAtTheTopOfTheCard() {
        assertTrue(DictionaryOverlayPolicy.shouldCollapse(48f, 48f, scrollAtTop = true))
        assertFalse(DictionaryOverlayPolicy.shouldCollapse(80f, 48f, scrollAtTop = false))
        assertFalse(DictionaryOverlayPolicy.shouldCollapse(47f, 48f, scrollAtTop = true))
    }
}
