package com.mangoloads.currentlens

/**
 * Calm optical tier for the transient word-definition popup.
 *
 * Keep this surface aligned with the shared Prismal transient material: one existing
 * backdrop surface, restrained blur/refraction, near-neutral chromatic dispersion,
 * and a bounded tint. These are design inputs, not measured GPU-performance claims.
 */
internal object DefinitionGlassSurfacePolicy {
    const val CORNER_RADIUS_DP = 22f
    const val BLUR_RADIUS_DP = 5f
    const val REFRACTION_HEIGHT_PX = 2.5f
    const val REFRACTION_AMOUNT_PX = 3.5f
    const val CHROMATIC_ABERRATION = 0.001f
    const val TINT_SCALE = 0.42f
    const val MIN_TINT_ALPHA = 0.08f
    const val MAX_TINT_ALPHA = 0.22f

    private const val FALLBACK_GLASS_ALPHA = 0.32f

    fun tintAlpha(glassAlpha: Float): Float {
        val safeAlpha = if (glassAlpha.isFinite()) {
            glassAlpha.coerceIn(0f, 1f)
        } else {
            FALLBACK_GLASS_ALPHA
        }
        return (safeAlpha * TINT_SCALE).coerceIn(MIN_TINT_ALPHA, MAX_TINT_ALPHA)
    }
}
