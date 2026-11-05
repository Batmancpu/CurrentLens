package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionaryOverlayPolicyTest {
    @Test
    fun collapsedCardShowsOnlyFirstMeaningAndHidesSecondaryContent() {
        assertEquals(1, DictionaryOverlayPolicy.visibleMeanings(5, false))
        assertEquals(0, DictionaryOverlayPolicy.visibleSynonyms(8, false))
        assertFalse(DictionaryOverlayPolicy.shouldShowRelatedWords(false, 8))
        assertFalse(DictionaryOverlayPolicy.shouldShowAttribution(false, true))
        assertFalse(DictionaryOverlayPolicy.shouldShowCopy(false, true))
    }

    @Test
    fun expandedCardCapsMeaningsAndRelatedWordsWithoutInventingContent() {
        assertEquals(5, DictionaryOverlayPolicy.visibleMeanings(9, true))
        assertEquals(3, DictionaryOverlayPolicy.visibleMeanings(3, true))
        assertEquals(8, DictionaryOverlayPolicy.visibleSynonyms(12, true))
        assertEquals(2, DictionaryOverlayPolicy.visibleSynonyms(2, true))
        assertEquals(0, DictionaryOverlayPolicy.visibleMeanings(-1, true))
        assertTrue(DictionaryOverlayPolicy.shouldShowRelatedWords(true, 2))
        assertTrue(DictionaryOverlayPolicy.shouldShowAttribution(true, true))
    }

    @Test
    fun copyRequiresExpandedStateAndCopyableContent() {
        assertFalse(DictionaryOverlayPolicy.shouldShowCopy(false, true))
        assertFalse(DictionaryOverlayPolicy.shouldShowCopy(true, false))
        assertTrue(DictionaryOverlayPolicy.shouldShowCopy(true, true))
    }

    @Test
    fun compactAndExpandedCardsUseBoundedDimensions() {
        assertEquals(320, DictionaryOverlayPolicy.MAX_WIDTH_DP)
        assertEquals(116, DictionaryOverlayPolicy.COMPACT_MAX_HEIGHT_DP)
        assertEquals(420, DictionaryOverlayPolicy.EXPANDED_MAX_HEIGHT_DP)
        assertEquals(2, DictionaryOverlayPolicy.COLLAPSED_MEANING_MAX_LINES)
    }

    @Test
    fun statusPolicyCoversLoadingFailureMissingAndEmptyStates() {
        assertEquals(DefinitionOverlayStatus.LOADING, DictionaryOverlayPolicy.status(true, null))
        assertEquals(DefinitionOverlayStatus.LOOKUP_FAILED, DictionaryOverlayPolicy.status(false, null))
        assertEquals(DefinitionOverlayStatus.DICTIONARY_MISSING,
            DictionaryOverlayPolicy.status(false, Definition("test", emptyList(), dictionaryAvailable = false)))
        assertEquals(DefinitionOverlayStatus.NO_ENTRY,
            DictionaryOverlayPolicy.status(false, Definition("test", emptyList())))
        assertEquals(DefinitionOverlayStatus.AVAILABLE,
            DictionaryOverlayPolicy.status(false, Definition("test", listOf("noun · a test definition"))))
    }

    @Test
    fun upwardSwipeExpandsOnlyAfterTheGestureThreshold() {
        assertFalse(DictionaryOverlayPolicy.shouldExpand(-47f, 48f))
        assertTrue(DictionaryOverlayPolicy.shouldExpand(-48f, 48f))
        assertFalse(DictionaryOverlayPolicy.shouldExpand(Float.NaN, 48f))
    }

    @Test
    fun downwardSwipeCollapsesOnlyAtTheTopOfTheCard() {
        assertTrue(DictionaryOverlayPolicy.shouldCollapse(48f, 48f, true))
        assertFalse(DictionaryOverlayPolicy.shouldCollapse(80f, 48f, false))
        assertFalse(DictionaryOverlayPolicy.shouldCollapse(47f, 48f, true))
    }
}
