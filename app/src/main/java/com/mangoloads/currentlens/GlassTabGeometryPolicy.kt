package com.mangoloads.currentlens

/** Geometry for a larger optical droplet whose resting footprint matches one tab cell. */
internal object GlassTabGeometryPolicy {
    fun expandedDimension(contentPx: Float, bleedPerSidePx: Float): Float {
        if (!contentPx.isFinite() || !bleedPerSidePx.isFinite() || contentPx <= 0f) return 0f
        return contentPx + 2f * bleedPerSidePx.coerceAtLeast(0f)
    }

    fun tabCenterPx(contentStartPx: Float, selectedIndex: Float, tabWidthPx: Float): Float =
        contentStartPx + (selectedIndex.coerceIn(0f, 3f) + 0.5f) * tabWidthPx

    fun leftPx(centerPx: Float, expandedWidthPx: Float): Float =
        centerPx - expandedWidthPx.coerceAtLeast(0f) / 2f

    fun topPx(barHeightPx: Float, expandedHeightPx: Float): Float =
        (barHeightPx - expandedHeightPx) / 2f

    fun restingScale(contentPx: Float, expandedPx: Float): Float =
        if (contentPx > 0f && expandedPx > 0f) (contentPx / expandedPx).coerceIn(0f, 1f) else 1f

    fun horizontalScale(restingScale: Float, motionScale: Float, velocity: Float): Float {
        val normalizedVelocity = velocity.coerceIn(-0.2f, 0.2f)
        return restingScale * motionScale / (1f - normalizedVelocity * 0.75f)
    }

    fun verticalScale(restingScale: Float, motionScale: Float, velocity: Float): Float {
        val normalizedVelocity = velocity.coerceIn(-0.2f, 0.2f)
        return restingScale * motionScale * (1f - normalizedVelocity * 0.25f)
    }

    /** Fade the tab control only near the end of the search morph; do not clip its bleed. */
    fun barAlpha(progress: Float): Float =
        1f - ((progress.coerceIn(0f, 1f) - 0.78f) / 0.22f).coerceIn(0f, 1f)
}
