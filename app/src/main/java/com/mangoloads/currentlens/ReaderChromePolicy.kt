package com.mangoloads.currentlens

/** Scroll-direction hysteresis state; Compose is notified only when visibility changes. */
internal data class ReaderChromeScrollState(
    val visible: Boolean = true,
    val direction: Int = 0,
    val accumulatedDeltaPx: Int = 0
)

/** Small, deterministic policies for Reader controls and completion presentation. */
internal object ReaderChromePolicy {
    const val HIDE_THRESHOLD_DP = 24
    const val SHOW_THRESHOLD_DP = 12
    const val HIDE_SETTLE_DELAY_MS = 160L

    /** Any transient reader interaction that needs controls keeps the chrome visible. */
    fun shouldPinChrome(
        definitionOpen: Boolean,
        selectionActive: Boolean,
        imeVisible: Boolean,
        accessibilityEnabled: Boolean,
        textSizeMenuExpanded: Boolean
    ): Boolean = definitionOpen || selectionActive || imeVisible ||
        accessibilityEnabled || textSizeMenuExpanded

    /** Explicit reveal resets directional accumulation and any stale hide intent. */
    fun reveal(state: ReaderChromeScrollState): ReaderChromeScrollState =
        state.copy(visible = true, direction = 0, accumulatedDeltaPx = 0)

    const val TAP_REVEAL_MAX_DURATION_MS = 360L

    /**
     * A short, stationary content tap may reveal hidden controls. Selection, long-press,
     * drag, multi-touch and any pinned/transient state are deliberately excluded.
     */
    fun shouldRevealOnTap(
        durationMs: Long,
        deltaX: Float,
        deltaY: Float,
        touchSlopPx: Float,
        selectionActive: Boolean,
        chromePinned: Boolean,
        selectionPending: Boolean
    ): Boolean {
        if (selectionActive || chromePinned || selectionPending) return false
        if (durationMs !in 0L..TAP_REVEAL_MAX_DURATION_MS) return false
        if (!deltaX.isFinite() || !deltaY.isFinite() || !touchSlopPx.isFinite()) return false
        val slop = touchSlopPx.coerceAtLeast(0f)
        return deltaX * deltaX + deltaY * deltaY <= slop * slop
    }

    /**
     * Downward document movement hides chrome after a larger threshold; upward movement
     * reveals it after a smaller threshold. Direction changes reset accumulated distance.
     * Pinning (selection/definition/IME/accessibility/transient menu) and returning to the top always reveal.
     */
    fun onScroll(
        state: ReaderChromeScrollState,
        deltaY: Int,
        hideThresholdPx: Int,
        showThresholdPx: Int,
        pinned: Boolean,
        atTop: Boolean
    ): ReaderChromeScrollState {
        if (pinned || atTop) return reveal(state)
        if (deltaY == 0) return state
        val direction = if (deltaY > 0) 1 else -1
        val magnitude = kotlin.math.abs(deltaY.toLong()).coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
        val accumulated = if (state.direction == direction) {
            (state.accumulatedDeltaPx.toLong() + magnitude).coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
        } else {
            magnitude
        }
        val threshold = (if (direction > 0) hideThresholdPx else showThresholdPx).coerceAtLeast(1)
        if (accumulated < threshold) {
            return ReaderChromeScrollState(
                visible = state.visible,
                direction = direction,
                accumulatedDeltaPx = accumulated
            )
        }
        return ReaderChromeScrollState(visible = direction < 0)
    }
    const val MIN_FONT_SIZE_SP = 16
    const val MAX_FONT_SIZE_SP = 28

    fun adjustFontSize(currentSp: Int, delta: Int): Int =
        (currentSp.coerceIn(MIN_FONT_SIZE_SP, MAX_FONT_SIZE_SP) + delta)
            .coerceIn(MIN_FONT_SIZE_SP, MAX_FONT_SIZE_SP)

    fun shouldShowCompletion(
        reachedBottom: Boolean,
        definitionOpen: Boolean,
        dismissed: Boolean,
        selectionActive: Boolean = false,
        transientControlOpen: Boolean = false
    ): Boolean = reachedBottom && !definitionOpen && !dismissed &&
        !selectionActive && !transientControlOpen
}
