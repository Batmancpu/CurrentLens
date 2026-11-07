package com.mangoloads.currentlens

/**
 * Shared tokens for supporting content surfaces that borrow Liquid Glass tint and rim
 * without adding live backdrop capture, blur, refraction or a second material pipeline.
 */
internal object GlassSurfacePolicy {
    const val SUPPORTING_CORNER_RADIUS_DP = 22f
    const val SUPPORTING_VALUE_SIZE_SP = 20f

    private const val SUPPORTING_SURFACE_ALPHA_DARK = 0.96f
    private const val SUPPORTING_SURFACE_ALPHA_LIGHT = 0.985f
    private const val SUPPORTING_TINT_ALPHA_DARK = 0.085f
    private const val SUPPORTING_TINT_ALPHA_LIGHT = 0.045f
    private const val SUPPORTING_RIM_ALPHA_DARK = 0.22f
    private const val SUPPORTING_RIM_ALPHA_LIGHT = 0.14f

    fun supportingSurfaceAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_SURFACE_ALPHA_DARK else SUPPORTING_SURFACE_ALPHA_LIGHT

    fun supportingTintAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_TINT_ALPHA_DARK else SUPPORTING_TINT_ALPHA_LIGHT

    fun supportingRimAlpha(dark: Boolean): Float =
        if (dark) SUPPORTING_RIM_ALPHA_DARK else SUPPORTING_RIM_ALPHA_LIGHT
}
