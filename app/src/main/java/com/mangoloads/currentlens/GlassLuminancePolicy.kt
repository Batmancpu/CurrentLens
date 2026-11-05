package com.mangoloads.currentlens

import kotlin.math.pow

/**
 * Relative-luminance and foreground selection for the shared 8x8 glass probe.
 *
 * RGB channels are converted from sRGB to linear light before WCAG luminance
 * weighting; comparing gamma-encoded channel values makes mid-tones look too bright.
 */
internal object GlassLuminancePolicy {
    private const val DARK_FOREGROUND_CROSSOVER = 0.17912878f

    fun averageRelativeLuminance(argbPixels: IntArray): Float {
        if (argbPixels.isEmpty()) return 0.5f
        val total = argbPixels.sumOf { argb ->
            val red = srgbToLinear((argb ushr 16) and 0xFF)
            val green = srgbToLinear((argb ushr 8) and 0xFF)
            val blue = srgbToLinear(argb and 0xFF)
            0.2126 * red + 0.7152 * green + 0.0722 * blue
        }
        return (total / argbPixels.size).toFloat().coerceIn(0f, 1f)
    }

    /** Chooses whichever monochrome foreground has the stronger WCAG contrast. */
    fun prefersDarkForeground(relativeLuminance: Float): Boolean {
        val luminance = if (relativeLuminance.isFinite()) {
            relativeLuminance.coerceIn(0f, 1f)
        } else {
            0.5f
        }
        return luminance >= DARK_FOREGROUND_CROSSOVER
    }

    private fun srgbToLinear(channel: Int): Double {
        val encoded = channel.coerceIn(0, 255) / 255.0
        return if (encoded <= 0.04045) {
            encoded / 12.92
        } else {
            ((encoded + 0.055) / 1.055).pow(2.4)
        }
    }
}
