package com.mangoloads.currentlens

/** Directional hysteresis state for the one shared home navigation component. */
internal data class FeedNavigationScrollState(
    val compact: Boolean = false,
    val direction: Int = 0,
    val accumulatedDeltaPx: Int = 0
)

/**
 * Scroll down through a feed to compact the navigation; scroll up to restore it.
 * The larger hide threshold and smaller show threshold avoid flicker around touch slop.
 */
internal object FeedNavigationPolicy {
    const val HIDE_THRESHOLD_DP = 44
    const val SHOW_THRESHOLD_DP = 16

    /** The selected droplet exists only in expanded navigation and respects Reduce Motion. */
    fun shouldAnimateDroplet(compact: Boolean, reduceMotion: Boolean): Boolean =
        !compact && !reduceMotion

    fun update(
        state: FeedNavigationScrollState,
        deltaPx: Int,
        hideThresholdPx: Int,
        showThresholdPx: Int,
        eligible: Boolean,
        atTop: Boolean
    ): FeedNavigationScrollState {
        if (!eligible || atTop) return FeedNavigationScrollState()
        if (deltaPx == 0) return state

        // Positive nested-scroll deltas mean the list advances down the document.
        val direction = if (deltaPx > 0) 1 else -1
        val magnitude = kotlin.math.abs(deltaPx.toLong())
            .coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
        val accumulated = if (state.direction == direction) {
            (state.accumulatedDeltaPx.toLong() + magnitude)
                .coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
        } else {
            magnitude
        }
        val threshold = (if (direction > 0) hideThresholdPx else showThresholdPx)
            .coerceAtLeast(1)
        if (accumulated < threshold) {
            return FeedNavigationScrollState(
                compact = state.compact,
                direction = direction,
                accumulatedDeltaPx = accumulated
            )
        }
        return FeedNavigationScrollState(compact = direction > 0)
    }
}

/** Mutable event accumulator kept outside Compose state; only compact-mode transitions recompose. */
internal class FeedNavigationScrollTracker {
    private var state = FeedNavigationScrollState()

    fun onScroll(
        deltaPx: Int,
        hideThresholdPx: Int,
        showThresholdPx: Int,
        eligible: Boolean,
        atTop: Boolean = false
    ): Boolean {
        state = FeedNavigationPolicy.update(
            state = state,
            deltaPx = deltaPx,
            hideThresholdPx = hideThresholdPx,
            showThresholdPx = showThresholdPx,
            eligible = eligible,
            atTop = atTop
        )
        return state.compact
    }

    fun reset(): Boolean {
        state = FeedNavigationScrollState()
        return false
    }
}
