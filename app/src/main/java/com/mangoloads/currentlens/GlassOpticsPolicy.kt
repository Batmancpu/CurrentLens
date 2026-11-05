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
        val collapsed = 1f - progress
        return GlassOpticsProfile(
            blurDp = 5.5f,
            refractionHeightDp = 9f + 3f * collapsed,
            refractionAmountDp = 14f + 8f * collapsed,
            chromaticAberration = 0.012f + 0.028f * collapsed,
            specularAlpha = 0.42f,
            shadowAlpha = 0.20f,
            insetAlpha = 0.04f,
            tintAlpha = 0.16f
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
