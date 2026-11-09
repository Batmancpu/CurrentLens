package com.mangoloads.currentlens

/**
 * Resolves the dictionary popup's surface treatment from the app's saved
 * Reduce Transparency preference and the platform's blur-behind capability.
 *
 * The reduced mode is deliberately opaque and never requests window blur.
 */
internal data class DictionarySurfaceAppearance(
    val blurBehindEnabled: Boolean,
    val surfaceAlpha: Float
)

internal object DictionarySurfacePolicy {
    const val REDUCED_TRANSPARENCY_ALPHA = 0.985f
    const val MODERN_GLASS_ALPHA = 0.86f
    const val LEGACY_SURFACE_ALPHA = 0.94f

    fun resolve(
        reduceTransparency: Boolean,
        supportsBlurBehind: Boolean
    ): DictionarySurfaceAppearance = when {
        reduceTransparency -> DictionarySurfaceAppearance(
            blurBehindEnabled = false,
            surfaceAlpha = REDUCED_TRANSPARENCY_ALPHA
        )
        supportsBlurBehind -> DictionarySurfaceAppearance(
            blurBehindEnabled = true,
            surfaceAlpha = MODERN_GLASS_ALPHA
        )
        else -> DictionarySurfaceAppearance(
            blurBehindEnabled = false,
            surfaceAlpha = LEGACY_SURFACE_ALPHA
        )
    }
}
