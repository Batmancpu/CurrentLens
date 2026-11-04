package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionarySelectionPolicyTest {
    @Test
    fun doesNotAutoDefineBeforeSelectionGracePeriodEnds() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                "Commonwealth",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS - 1
            )
        )
    }

    @Test
    fun keepsA650MillisecondGraceWindowForNativeSelectionActions() {
        assertEquals(650L, DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS)
        assertNull(DictionarySelectionPolicy.autoDefineWord("Commonwealth", 649L))
        assertEquals(
            "commonwealth",
            DictionarySelectionPolicy.autoDefineWord("Commonwealth", 650L)
        )
    }

    @Test
    fun autoDefinesOneStableWordAfterGracePeriod() {
        assertEquals(
            "commonwealth",
            DictionarySelectionPolicy.autoDefineWord(
                "Commonwealth",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS
            )
        )
    }

    @Test
    fun leavesExtendedMultiWordSelectionToNativeActions() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                "rule of law",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS + 250
            )
        )
    }

    @Test
    fun cancelsAutoDefineWhenASelectionDragExceedsPlatformTouchSlop() {
        assertFalse(
            DictionarySelectionPolicy.shouldCancelAutoDefineForMovement(
                deltaX = 6f,
                deltaY = 8f,
                touchSlopPx = 10f
            )
        )
        assertTrue(
            DictionarySelectionPolicy.shouldCancelAutoDefineForMovement(
                deltaX = 0f,
                deltaY = 11f,
                touchSlopPx = 10f
            )
        )
        assertTrue(
            DictionarySelectionPolicy.shouldCancelAutoDefineForMovement(
                deltaX = -11f,
                deltaY = 0f,
                touchSlopPx = 10f
            )
        )
    }

    @Test
    fun ignoresNonFiniteGestureSamplesWhenDecidingWhetherToCancel() {
        assertFalse(
            DictionarySelectionPolicy.shouldCancelAutoDefineForMovement(
                deltaX = Float.NaN,
                deltaY = 20f,
                touchSlopPx = 10f
            )
        )
        assertFalse(
            DictionarySelectionPolicy.shouldCancelAutoDefineForMovement(
                deltaX = 20f,
                deltaY = 0f,
                touchSlopPx = Float.NaN
            )
        )
    }

    @Test
    fun manualDefineNormalizesOneWordForTheCurrentSelectionRequest() {
        assertEquals(
            "commonwealth",
            DictionarySelectionPolicy.manualDefineWord("Commonwealth", 4, 4)
        )
    }

    @Test
    fun manualDefineRejectsAResultFromAnEndedSelectionMode() {
        assertNull(DictionarySelectionPolicy.manualDefineWord("Commonwealth", 4, 5))
    }

    @Test
    fun manualDefineLeavesMultiWordSelectionToNativeActions() {
        assertNull(DictionarySelectionPolicy.manualDefineWord("rule of law", 4, 4))
    }

    @Test
    fun ignoresEmptyOrPunctuationOnlySelection() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                " — ",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS + 250
            )
        )
    }
}
