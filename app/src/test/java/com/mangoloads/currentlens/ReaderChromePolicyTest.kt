package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderChromePolicyTest {
    @Test
    fun fontSizeChangesInSingleStepsWithinAccessibleBounds() {
        assertEquals(21, ReaderChromePolicy.adjustFontSize(20, 1))
        assertEquals(19, ReaderChromePolicy.adjustFontSize(20, -1))
        assertEquals(16, ReaderChromePolicy.adjustFontSize(16, -1))
        assertEquals(28, ReaderChromePolicy.adjustFontSize(28, 1))
    }

    @Test
    fun invalidStoredFontSizeIsClampedBeforeApplyingChange() {
        assertEquals(28, ReaderChromePolicy.adjustFontSize(40, 1))
        assertEquals(16, ReaderChromePolicy.adjustFontSize(10, -1))
    }

    @Test
    fun completionAppearsOnlyAtEndWithoutOpenDefinitionOrDismissal() {
        assertTrue(ReaderChromePolicy.shouldShowCompletion(true, false, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(false, false, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(true, true, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(true, false, true))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(true, false, false, selectionActive = true))
    }
    @Test
    fun downwardScrollHidesOnlyAfterLargerThreshold() {
        var state = ReaderChromeScrollState()
        state = ReaderChromePolicy.onScroll(state, 10, 24, 12, pinned = false, atTop = false)
        assertTrue(state.visible)
        state = ReaderChromePolicy.onScroll(state, 14, 24, 12, pinned = false, atTop = false)
        assertFalse(state.visible)
    }

    @Test
    fun upwardScrollRevealsAfterSmallerThresholdAndDirectionChangeResetsDistance() {
        var state = ReaderChromeScrollState(visible = false)
        state = ReaderChromePolicy.onScroll(state, 20, 24, 12, pinned = false, atTop = false)
        assertFalse(state.visible)
        state = ReaderChromePolicy.onScroll(state, -8, 24, 12, pinned = false, atTop = false)
        assertFalse(state.visible)
        assertEquals(8, state.accumulatedDeltaPx)
        state = ReaderChromePolicy.onScroll(state, -4, 24, 12, pinned = false, atTop = false)
        assertTrue(state.visible)
    }

    @Test
    fun pinnedChromeAndFeedTopAlwaysRevealControls() {
        val hidden = ReaderChromeScrollState(visible = false, direction = 1, accumulatedDeltaPx = 30)
        assertTrue(
            ReaderChromePolicy.onScroll(hidden, 4, 24, 12, pinned = true, atTop = false).visible
        )
        assertTrue(
            ReaderChromePolicy.onScroll(hidden, 4, 24, 12, pinned = false, atTop = true).visible
        )
    }

    @Test
    fun repeatedSameDirectionBelowThresholdAccumulatesButDirectionChangeResets() {
        var state = ReaderChromeScrollState()
        state = ReaderChromePolicy.onScroll(state, 10, 24, 12, pinned = false, atTop = false)
        state = ReaderChromePolicy.onScroll(state, 8, 24, 12, pinned = false, atTop = false)
        assertEquals(18, state.accumulatedDeltaPx)
        state = ReaderChromePolicy.onScroll(state, -4, 24, 12, pinned = false, atTop = false)
        assertEquals(4, state.accumulatedDeltaPx)
        assertEquals(-1, state.direction)
    }

}
