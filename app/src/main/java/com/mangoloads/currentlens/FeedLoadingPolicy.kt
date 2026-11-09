package com.mangoloads.currentlens

/** Delays cold-feed placeholders so fast local restores do not flash skeletons. */
internal object FeedLoadingPolicy {
    const val SKELETON_DELAY_MS = 180L

    fun shouldShowSkeletons(isLoading: Boolean, delayElapsed: Boolean): Boolean =
        isLoading && delayElapsed
}
