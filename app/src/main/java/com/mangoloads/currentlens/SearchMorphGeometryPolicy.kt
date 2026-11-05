package com.mangoloads.currentlens

/** One measured frame of the Search capsule moving from its tab to the IME dock. */
internal data class SearchMorphFrame(
    val widthPx: Float,
    val heightPx: Float,
    val offsetXPx: Float,
    val offsetYPx: Float,
    val cornerRadiusPx: Float,
    val fieldAlpha: Float,
    val tabBarAlpha: Float
)

/** Pure geometry and visual handoff policy for the Search-tab glass morph. */
internal object SearchMorphGeometryPolicy {
    private const val SURFACE_HANDOFF_PROGRESS = 0.18f

    private fun normalizedProgress(progress: Float): Float =
        if (progress.isFinite()) progress.coerceIn(0f, 1f) else 0f

    private fun finiteOr(value: Float, fallback: Float): Float =
        if (value.isFinite()) value else fallback

    private fun positiveOr(value: Float, fallback: Float): Float =
        if (value.isFinite() && value > 0f) value else fallback.takeIf { it.isFinite() && it > 0f } ?: 1f

    fun interpolate(start: Float, end: Float, progress: Float): Float {
        val from = finiteOr(start, 0f)
        val to = finiteOr(end, from)
        val t = normalizedProgress(progress)
        return from + (to - from) * t
    }

    /**
     * Interpolates actual bounds and returns an offset relative to the dock container.
     * The rendered shape therefore receives its current size before PrismalCapsule
     * computes its radius; it is never distorted by a non-uniform graphics-layer scale.
     */
    fun frame(
        sourceLeftPx: Float,
        sourceTopPx: Float,
        sourceWidthPx: Float,
        sourceHeightPx: Float,
        dockLeftPx: Float,
        dockTopPx: Float,
        dockWidthPx: Float,
        dockHeightPx: Float,
        progress: Float
    ): SearchMorphFrame {
        val dockLeft = finiteOr(dockLeftPx, finiteOr(sourceLeftPx, 0f))
        val dockTop = finiteOr(dockTopPx, finiteOr(sourceTopPx, 0f))
        val sourceLeft = finiteOr(sourceLeftPx, dockLeft)
        val sourceTop = finiteOr(sourceTopPx, dockTop)
        val sourceWidth = positiveOr(sourceWidthPx, dockWidthPx)
        val sourceHeight = positiveOr(sourceHeightPx, dockHeightPx)
        val targetWidth = positiveOr(dockWidthPx, sourceWidth)
        val targetHeight = positiveOr(dockHeightPx, sourceHeight)
        val t = normalizedProgress(progress)
        val width = interpolate(sourceWidth, targetWidth, t).coerceAtLeast(1f)
        val height = interpolate(sourceHeight, targetHeight, t).coerceAtLeast(1f)
        val left = interpolate(sourceLeft, dockLeft, t)
        val top = interpolate(sourceTop, dockTop, t)

        return SearchMorphFrame(
            widthPx = width,
            heightPx = height,
            offsetXPx = left - dockLeft,
            offsetYPx = top - dockTop,
            cornerRadiusPx = minOf(width, height) * 0.5f,
            fieldAlpha = fieldAlpha(t),
            tabBarAlpha = tabBarAlpha(t)
        )
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

    /** The tab and Search capsule are complementary surfaces on one navigation plane. */
    fun tabBarAlpha(progress: Float): Float =
        1f - (normalizedProgress(progress) / SURFACE_HANDOFF_PROGRESS).coerceIn(0f, 1f)

    fun fieldAlpha(progress: Float): Float = 1f - tabBarAlpha(progress)
}
