package com.mangoloads.currentlens

/** Small, deterministic policies for Reader controls and completion presentation. */
internal object ReaderChromePolicy {
    const val MIN_FONT_SIZE_SP = 16
    const val MAX_FONT_SIZE_SP = 28

    fun adjustFontSize(currentSp: Int, delta: Int): Int =
        (currentSp.coerceIn(MIN_FONT_SIZE_SP, MAX_FONT_SIZE_SP) + delta)
            .coerceIn(MIN_FONT_SIZE_SP, MAX_FONT_SIZE_SP)

    fun shouldShowCompletion(
        reachedBottom: Boolean,
        definitionOpen: Boolean,
        dismissed: Boolean
    ): Boolean = reachedBottom && !definitionOpen && !dismissed
}
