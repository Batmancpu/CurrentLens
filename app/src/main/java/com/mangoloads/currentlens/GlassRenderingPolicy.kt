package com.mangoloads.currentlens

/**
 * Shared, testable fallback for users who prefer less transparency and fewer live optics.
 * The normal material remains unchanged unless the local preference is enabled.
 */
internal object GlassRenderingPolicy {
    const val REDUCED_MIN_TINT_ALPHA = 0.24f
    private const val REDUCED_SPECULAR_SCALE = 0.42f

    fun blurDp(baseDp: Float, reduceTransparency: Boolean): Float =
        if (reduceTransparency) 0f else nonNegative(baseDp)

    fun refractionDp(baseDp: Float, reduceTransparency: Boolean): Float =
        if (reduceTransparency) 0f else nonNegative(baseDp)

    fun refractionPx(basePx: Float, reduceTransparency: Boolean): Float =
        if (reduceTransparency) 0f else nonNegative(basePx)

    fun chromaticAberration(base: Float, reduceTransparency: Boolean): Float =
        if (reduceTransparency) 0f else nonNegative(base)

    fun depthEnabled(base: Boolean, reduceTransparency: Boolean): Boolean =
        base && !reduceTransparency

    fun specularAlpha(base: Float, reduceTransparency: Boolean): Float {
        val alpha = unitInterval(base)
        return if (reduceTransparency) alpha * REDUCED_SPECULAR_SCALE else alpha
    }

    fun shadowAlpha(base: Float, reduceTransparency: Boolean): Float =
        if (reduceTransparency) 0f else unitInterval(base)

    fun tintAlpha(base: Float, reduceTransparency: Boolean): Float {
        val alpha = unitInterval(base)
        return if (reduceTransparency) maxOf(alpha, REDUCED_MIN_TINT_ALPHA) else alpha
    }

    private fun nonNegative(value: Float): Float =
        if (value.isFinite()) value.coerceAtLeast(0f) else 0f

    private fun unitInterval(value: Float): Float =
        if (value.isFinite()) value.coerceIn(0f, 1f) else 0f
}
