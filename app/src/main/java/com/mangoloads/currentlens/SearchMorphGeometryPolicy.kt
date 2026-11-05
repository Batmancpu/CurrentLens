package com.mangoloads.currentlens

/** Pure geometry and visual handoff policy for the Search-tab glass morph. */
internal object SearchMorphGeometryPolicy {
    private fun normalizedProgress(progress: Float): Float =
        if (progress.isFinite()) progress.coerceIn(0f, 1f) else 0f

    fun interpolate(start: Float, end: Float, progress: Float): Float {
        val from = if (start.isFinite()) start else 0f
        val to = if (end.isFinite()) end else from
        val t = normalizedProgress(progress)
        return from + (to - from) * t
    }

    /** Scale the dock-sized field from the selected tab's exact resting footprint. */
    fun scaleFromAnchor(sourceDimensionPx: Float, targetDimensionPx: Float, progress: Float): Float {
        if (!sourceDimensionPx.isFinite() || !targetDimensionPx.isFinite() ||
            sourceDimensionPx <= 0f || targetDimensionPx <= 0f
        ) return 1f
        return interpolate(sourceDimensionPx / targetDimensionPx, 1f, progress)
    }

    /** Initial icon translation that centres its 20dp glyph inside the collapsed tab cell. */
    fun collapsedIconOffsetPx(
        sourceWidthPx: Float,
        iconWidthPx: Float,
        leadingInsetPx: Float
    ): Float {
        if (!sourceWidthPx.isFinite() || !iconWidthPx.isFinite() ||
            !leadingInsetPx.isFinite() || sourceWidthPx <= 0f ||
            iconWidthPx < 0f || leadingInsetPx < 0f
        ) return 0f
        return ((sourceWidthPx - iconWidthPx) / 2f - leadingInsetPx).coerceAtLeast(0f)
    }

    /** Moves the Search glyph from the tab centre to the capsule's leading inset. */
    fun iconOffset(collapsedOffsetPx: Float, progress: Float): Float {
        val offset = if (collapsedOffsetPx.isFinite()) collapsedOffsetPx.coerceAtLeast(0f) else 0f
        return offset * (1f - normalizedProgress(progress))
    }

    /** With Reduce Motion enabled, render the target geometry without intermediate frames. */
    fun effectiveProgress(
        animatedProgress: Float,
        expanded: Boolean,
        reduceMotion: Boolean
    ): Float = if (reduceMotion) {
        if (expanded) 1f else 0f
    } else {
        normalizedProgress(animatedProgress)
    }

    fun collapsedRefractionFactor(progress: Float): Float =
        1f - normalizedProgress(progress)

    /** The tab plane yields as the same capsule becomes visible from its Search anchor. */
    fun tabBarAlpha(progress: Float): Float =
        1f - (normalizedProgress(progress) / 0.18f).coerceIn(0f, 1f)

    fun fieldAlpha(progress: Float): Float =
        (normalizedProgress(progress) / 0.12f).coerceIn(0f, 1f)
}
