package com.mangoloads.currentlens

import kotlin.math.abs

/** Calibrated, testable optical response for the navigation droplet and Search control. */
internal data class GlassOpticsProfile(
    val blurDp: Float,
    val refractionHeightDp: Float,
    val refractionAmountDp: Float,
    val chromaticAberration: Float,
    val specularAlpha: Float,
    val shadowAlpha: Float,
    val insetAlpha: Float,
    val tintAlpha: Float
)

internal object GlassOpticsPolicy {
    fun interactionProgress(progress: Float, reduceMotion: Boolean = false): Float {
        if (reduceMotion || !progress.isFinite()) return 0f
        return progress.coerceIn(0f, 1f)
    }

    fun pressedScale(reduceMotion: Boolean): Float = if (reduceMotion) 1f else 1.12f

    fun droplet(
        pressProgress: Float,
        reduceMotion: Boolean = false
    ): GlassOpticsProfile {
        val p = interactionProgress(pressProgress, reduceMotion)
        return GlassOpticsProfile(
            blurDp = 5.5f + 1.5f * p,
            refractionHeightDp = 10f + 8f * p,
            refractionAmountDp = 18f + 18f * p,
            chromaticAberration = 0.015f + 0.065f * p,
            specularAlpha = 0.34f + 0.40f * p,
            shadowAlpha = 0.18f + 0.36f * p,
            insetAlpha = 0.04f + 0.22f * p,
            tintAlpha = 0.075f + 0.04f * p
        )
    }

    fun search(morphProgress: Float): GlassOpticsProfile {
        val progress = if (morphProgress.isFinite()) morphProgress.coerceIn(0f, 1f) else 0f
        // A larger morph represents a thicker control: increase lensing, edge light
        // and separation with area instead of making the expanded field optically flatter.
        return GlassOpticsProfile(
            blurDp = 5.5f + 1.0f * progress,
            refractionHeightDp = 9f + 4f * progress,
            refractionAmountDp = 14f + 8f * progress,
            chromaticAberration = 0.012f + 0.012f * progress,
            specularAlpha = 0.34f + 0.12f * progress,
            shadowAlpha = 0.16f + 0.16f * progress,
            insetAlpha = 0.03f + 0.04f * progress,
            tintAlpha = 0.13f + 0.02f * progress
        )
    }

    /** Adds a small contrast reserve only as the sampled scene approaches either extreme. */
    fun adaptiveTintAlpha(
        baseAlpha: Float,
        luminance: Float,
        minAlpha: Float,
        maxAlpha: Float
    ): Float {
        val minimum = minAlpha.coerceIn(0f, 1f)
        val maximum = maxAlpha.coerceIn(minimum, 1f)
        val base = if (baseAlpha.isFinite()) baseAlpha.coerceIn(0f, 1f) else minimum
        val sample = if (luminance.isFinite()) luminance.coerceIn(0f, 1f) else 0.5f
        val contrastDemand = abs(sample - 0.5f) * 2f
        return (base + 0.025f * contrastDemand).coerceIn(minimum, maximum)
    }
}
