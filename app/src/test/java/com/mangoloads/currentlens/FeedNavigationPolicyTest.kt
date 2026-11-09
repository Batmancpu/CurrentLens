package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedNavigationPolicyTest {
    @Test
    fun dropletAnimationIsDisabledWhenCompactOrReduceMotionIsEnabled() {
        assertTrue(FeedNavigationPolicy.shouldAnimateDroplet(compact = false, reduceMotion = false))
        assertFalse(FeedNavigationPolicy.shouldAnimateDroplet(compact = true, reduceMotion = false))
        assertFalse(FeedNavigationPolicy.shouldAnimateDroplet(compact = false, reduceMotion = true))
        assertFalse(FeedNavigationPolicy.shouldAnimateDroplet(compact = true, reduceMotion = true))
    }

    @Test
    fun downwardFeedScrollCompactsOnlyAfterHysteresisThreshold() {
        var state = FeedNavigationScrollState()
        state = FeedNavigationPolicy.update(state, 20, 44, 16, eligible = true, atTop = false)
        assertFalse(state.compact)
        state = FeedNavigationPolicy.update(state, 24, 44, 16, eligible = true, atTop = false)
        assertTrue(state.compact)
    }

    @Test
    fun upwardScrollRestoresWithSmallerThreshold() {
        var state = FeedNavigationScrollState(compact = true)
        state = FeedNavigationPolicy.update(state, -8, 44, 16, eligible = true, atTop = false)
        assertTrue(state.compact)
        state = FeedNavigationPolicy.update(state, -8, 44, 16, eligible = true, atTop = false)
        assertFalse(state.compact)
    }

    @Test
    fun directionChangeResetsAccumulatedDistance() {
        var state = FeedNavigationScrollState()
        state = FeedNavigationPolicy.update(state, 30, 44, 16, eligible = true, atTop = false)
        state = FeedNavigationPolicy.update(state, -10, 44, 16, eligible = true, atTop = false)
        assertFalse(state.compact)
        assertEquals(10, state.accumulatedDeltaPx)
        assertEquals(-1, state.direction)
    }

    @Test
    fun ineligibleScreensAndFeedTopAlwaysExpandNavigation() {
        val compact = FeedNavigationScrollState(compact = true, direction = 1, accumulatedDeltaPx = 64)
        assertFalse(
            FeedNavigationPolicy.update(compact, 10, 44, 16, eligible = false, atTop = false).compact
        )
        assertFalse(
            FeedNavigationPolicy.update(compact, 10, 44, 16, eligible = true, atTop = true).compact
        )
    }

    @Test
    fun trackerReportsOnlyCompactStateAndCanReset() {
        val tracker = FeedNavigationScrollTracker()
        assertFalse(tracker.onScroll(43, 44, 16, eligible = true))
        assertTrue(tracker.onScroll(1, 44, 16, eligible = true))
        assertFalse(tracker.reset())
    }
}
