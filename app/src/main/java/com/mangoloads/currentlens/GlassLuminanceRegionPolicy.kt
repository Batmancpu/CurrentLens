package com.mangoloads.currentlens

internal data class GlassLuminanceRegion(
    val leftPx: Int,
    val topPx: Int,
    val widthPx: Int,
    val heightPx: Int
)

/** Pure geometry for the bounded source region used by the shared glass probe. */
internal object GlassLuminanceRegionPolicy {
    fun bottomBand(
        sourceWidthPx: Int,
        sourceHeightPx: Int,
        requestedHeightPx: Int
    ): GlassLuminanceRegion? {
        if (sourceWidthPx <= 0 || sourceHeightPx <= 0 || requestedHeightPx <= 0) return null
        val height = requestedHeightPx.coerceAtMost(sourceHeightPx)
        return GlassLuminanceRegion(
            leftPx = 0,
            topPx = sourceHeightPx - height,
            widthPx = sourceWidthPx,
            heightPx = height
        )
    }
}
