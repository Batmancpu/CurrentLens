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
    private const val SUPPORTING_TINT_ALPHA_DARK = 0.11f
    private const val SUPPORTING_TINT_ALPHA_LIGHT = 0.07f
    private const val SUPPORTING_RIM_ALPHA_DARK = 0.20f
    private const val SUPPORTING_RIM_ALPHA_LIGHT = 0.13f

    fun supportingSurfaceAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_SURFACE_ALPHA_DARK else SUPPORTING_SURFACE_ALPHA_LIGHT

    fun supportingTintAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_TINT_ALPHA_DARK else SUPPORTING_TINT_ALPHA_LIGHT

    fun supportingRimAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_RIM_ALPHA_DARK else SUPPORTING_RIM_ALPHA_LIGHT
}
