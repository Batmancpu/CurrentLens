package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedLoadingPolicyTest {
    @Test
    fun fastFeedLoadDoesNotFlashSkeletons() {
        assertFalse(
            FeedLoadingPolicy.shouldShowSkeletons(isLoading = true, delayElapsed = false)
        )
    }

    @Test
    fun sustainedFeedLoadShowsSkeletonsAfterTheRevealDelay() {
        assertTrue(
            FeedLoadingPolicy.shouldShowSkeletons(isLoading = true, delayElapsed = true)
        )
    }

    @Test
    fun completedFeedLoadHidesSkeletonsImmediately() {
        assertFalse(
            FeedLoadingPolicy.shouldShowSkeletons(isLoading = false, delayElapsed = true)
        )
    }

    @Test
    fun revealDelayIsShortAndBounded() {
        assertTrue(FeedLoadingPolicy.SKELETON_DELAY_MS in 120L..250L)
    }
}
