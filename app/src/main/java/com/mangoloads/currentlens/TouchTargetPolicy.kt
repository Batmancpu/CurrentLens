package com.mangoloads.currentlens

/** Minimum layout target for icon-only actions that must remain easy to activate. */
internal object TouchTargetPolicy {
    const val MIN_ICON_TARGET_DP = 48

    fun iconTargetDp(requestedDp: Int): Int =
        requestedDp.coerceAtLeast(MIN_ICON_TARGET_DP)
}
