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
        assertFalse(
            ReaderChromePolicy.shouldShowCompletion(true, false, false, transientControlOpen = true)
        )
    }
    @Test
    fun textSizeMenuAndOtherTransientStatesPinReaderChrome() {
        assertTrue(
            ReaderChromePolicy.shouldPinChrome(
                definitionOpen = true,
                selectionActive = false,
                imeVisible = false,
                accessibilityEnabled = false,
                textSizeMenuExpanded = false
            )
        )
        assertTrue(
            ReaderChromePolicy.shouldPinChrome(
                definitionOpen = false,
                selectionActive = true,
                imeVisible = false,
                accessibilityEnabled = false,
                textSizeMenuExpanded = false
            )
        )
        assertTrue(
            ReaderChromePolicy.shouldPinChrome(
                definitionOpen = false,
                selectionActive = false,
                imeVisible = true,
                accessibilityEnabled = false,
                textSizeMenuExpanded = false
            )
        )
        assertTrue(
            ReaderChromePolicy.shouldPinChrome(
                definitionOpen = false,
                selectionActive = false,
                imeVisible = false,
                accessibilityEnabled = true,
                textSizeMenuExpanded = false
            )
        )
        val menuOpen = ReaderChromePolicy.shouldPinChrome(
            definitionOpen = false,
            selectionActive = false,
            imeVisible = false,
            accessibilityEnabled = false,
            textSizeMenuExpanded = true
        )
        assertTrue(menuOpen)
        assertTrue(
            ReaderChromePolicy.onScroll(
                state = ReaderChromeScrollState(visible = false),
                deltaY = 30,
                hideThresholdPx = 24,
                showThresholdPx = 12,
                pinned = menuOpen,
                atTop = false
            ).visible
        )
        assertFalse(
            ReaderChromePolicy.shouldPinChrome(
                definitionOpen = false,
                selectionActive = false,
                imeVisible = false,
                accessibilityEnabled = false,
                textSizeMenuExpanded = false
            )
        )
    }

    @Test
    fun explicitRevealRestoresHiddenChromeAndClearsDirectionalAccumulation() {
        val hidden = ReaderChromeScrollState(
            visible = false,
            direction = 1,
            accumulatedDeltaPx = 31
        )
        assertEquals(ReaderChromeScrollState(), ReaderChromePolicy.reveal(hidden))
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

    @Test
    fun shortStationaryTapCanRevealHiddenChrome() {
        assertTrue(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 220,
                deltaX = 3f,
                deltaY = 4f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = false
            )
        )
        assertTrue(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = ReaderChromePolicy.TAP_REVEAL_MAX_DURATION_MS,
                deltaX = 0f,
                deltaY = 0f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = false
            )
        )
    }

    @Test
    fun longPressAndMovementDoNotRevealChromeAsATap() {
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = ReaderChromePolicy.TAP_REVEAL_MAX_DURATION_MS + 1,
                deltaX = 0f,
                deltaY = 0f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = false
            )
        )
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 180,
                deltaX = 9f,
                deltaY = 0f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = false
            )
        )
    }

    @Test
    fun selectionAndPinnedReaderStatesSuppressTapReveal() {
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 180,
                deltaX = 2f,
                deltaY = 2f,
                touchSlopPx = 8f,
                selectionActive = true,
                chromePinned = false,
                selectionPending = false
            )
        )
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 180,
                deltaX = 2f,
                deltaY = 2f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = true,
                selectionPending = false
            )
        )
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 180,
                deltaX = 2f,
                deltaY = 2f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = true
            )
        )
    }

    @Test
    fun invalidTapMeasurementsAreRejected() {
        assertFalse(
            ReaderChromePolicy.shouldRevealOnTap(
                durationMs = 180,
                deltaX = Float.NaN,
                deltaY = 0f,
                touchSlopPx = 8f,
                selectionActive = false,
                chromePinned = false,
                selectionPending = false
            )
        )
    }

}
