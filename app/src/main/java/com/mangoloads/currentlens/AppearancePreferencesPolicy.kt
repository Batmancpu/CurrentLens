package com.mangoloads.currentlens

/** Pure bounds for the persisted glass alpha preference. */
internal object AppearancePreferencesPolicy {
    const val DEFAULT_GLASS_ALPHA = 0.28f
    private const val MIN_GLASS_ALPHA = 0.12f
    private const val MAX_GLASS_ALPHA = 0.55f

    fun normalizeGlassAlpha(value: Float): Float =
        if (value.isFinite()) value.coerceIn(MIN_GLASS_ALPHA, MAX_GLASS_ALPHA)
        else DEFAULT_GLASS_ALPHA
}
