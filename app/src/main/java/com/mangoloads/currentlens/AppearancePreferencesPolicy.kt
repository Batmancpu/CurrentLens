package com.mangoloads.currentlens

internal data class LocalAppearancePreferences(
    val glassAlpha: Float = AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA,
    val reduceTransparency: Boolean = false
)

/** Pure normalization for the two small appearance values restored with local app state. */
internal object AppearancePreferencesPolicy {
    const val DEFAULT_GLASS_ALPHA = 0.28f
    const val MIN_GLASS_ALPHA = 0.12f
    const val MAX_GLASS_ALPHA = 0.55f

    fun normalizeGlassAlpha(value: Float): Float =
        if (value.isFinite()) value.coerceIn(MIN_GLASS_ALPHA, MAX_GLASS_ALPHA)
        else DEFAULT_GLASS_ALPHA

    fun create(glassAlpha: Float, reduceTransparency: Boolean): LocalAppearancePreferences =
        LocalAppearancePreferences(
            glassAlpha = normalizeGlassAlpha(glassAlpha),
            reduceTransparency = reduceTransparency
        )
}
