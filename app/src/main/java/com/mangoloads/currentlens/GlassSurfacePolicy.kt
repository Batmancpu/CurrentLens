package com.mangoloads.currentlens

/**
 * Shared tokens for supporting content surfaces that borrow Liquid Glass tint and rim
 * without adding live backdrop capture, blur, refraction or a second material pipeline.
 */
internal object GlassSurfacePolicy {
    const val SUPPORTING_CORNER_RADIUS_DP = 22f
    const val SUPPORTING_VALUE_SIZE_SP = 20f
    const val TRANSIENT_BLUR_DP = 5f
    const val TRANSIENT_REFRACTION_HEIGHT_DP = 2.5f
    const val TRANSIENT_REFRACTION_AMOUNT_DP = 3.5f
    const val TRANSIENT_TINT_SCALE = 0.42f

    private const val SUPPORTING_SURFACE_ALPHA_DARK = 0.93f
    private const val SUPPORTING_SURFACE_ALPHA_LIGHT = 0.965f
    private const val SUPPORTING_TINT_DARK_CLEAR = 0.09f
    private const val SUPPORTING_TINT_DARK_FROSTED = 0.145f
    private const val SUPPORTING_TINT_LIGHT_CLEAR = 0.055f
    private const val SUPPORTING_TINT_LIGHT_FROSTED = 0.095f
    private const val SUPPORTING_RIM_DARK_CLEAR = 0.17f
    private const val SUPPORTING_RIM_DARK_FROSTED = 0.23f
    private const val SUPPORTING_RIM_LIGHT_CLEAR = 0.11f
    private const val SUPPORTING_RIM_LIGHT_FROSTED = 0.16f

    fun supportingSurfaceAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_SURFACE_ALPHA_DARK else SUPPORTING_SURFACE_ALPHA_LIGHT

    fun supportingTintAlpha(
        dark: Boolean,
        glassAlpha: Float = AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA
    ): Float {
        val progress = intensityProgress(glassAlpha)
        val clear = if (dark) SUPPORTING_TINT_DARK_CLEAR else SUPPORTING_TINT_LIGHT_CLEAR
        val frosted = if (dark) SUPPORTING_TINT_DARK_FROSTED else SUPPORTING_TINT_LIGHT_FROSTED
        return clear + (frosted - clear) * progress
    }

    fun supportingRimAlpha(
        dark: Boolean,
        glassAlpha: Float = AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA
    ): Float {
        val progress = intensityProgress(glassAlpha)
        val clear = if (dark) SUPPORTING_RIM_DARK_CLEAR else SUPPORTING_RIM_LIGHT_CLEAR
        val frosted = if (dark) SUPPORTING_RIM_DARK_FROSTED else SUPPORTING_RIM_LIGHT_FROSTED
        return clear + (frosted - clear) * progress
    }

    /**
     * Preview-only control tokens. The mini Search surface and selected tab should visibly
     * follow the same intensity preference as the outer Prismal material. Reduced
     * transparency uses a clearer, more opaque fallback without adding another blur pass.
     */
    fun previewControlSurfaceAlpha(glassAlpha: Float, reduceTransparency: Boolean): Float {
        val alpha = 0.38f + 0.18f * intensityProgress(glassAlpha)
        return if (reduceTransparency) maxOf(alpha, 0.62f) else alpha
    }

    fun previewControlRimAlpha(glassAlpha: Float, reduceTransparency: Boolean): Float {
        val alpha = 0.16f + 0.10f * intensityProgress(glassAlpha)
        return if (reduceTransparency) maxOf(alpha, 0.28f) else alpha
    }

    fun previewSelectedFillAlpha(glassAlpha: Float, reduceTransparency: Boolean): Float {
        val alpha = 0.10f + 0.12f * intensityProgress(glassAlpha)
        return if (reduceTransparency) maxOf(alpha, 0.20f) else alpha
    }

    private fun intensityProgress(glassAlpha: Float): Float {
        val normalized = AppearancePreferencesPolicy.normalizeGlassAlpha(glassAlpha)
        return ((normalized - AppearancePreferencesPolicy.MIN_GLASS_ALPHA) /
            (AppearancePreferencesPolicy.MAX_GLASS_ALPHA - AppearancePreferencesPolicy.MIN_GLASS_ALPHA))
            .coerceIn(0f, 1f)
    }
}


/**
 * Shared geometry for calm, supporting dashboard/stat surfaces. These values affect
 * layout only; they do not create additional blur, backdrop sampling or render passes.
 */
internal object SupportingCardGeometryPolicy {
    const val CONTENT_PADDING_DP = 16f
    const val CONTENT_GAP_DP = 6f
    const val SECTION_GAP_DP = 12f
    const val METRIC_GAP_DP = 9f
    const val METRIC_MIN_HEIGHT_DP = 88f
    const val DESTINATION_MIN_HEIGHT_DP = 80f
    const val STAT_ROW_MIN_HEIGHT_DP = 76f
    const val EMPTY_MIN_HEIGHT_DP = 104f
}
