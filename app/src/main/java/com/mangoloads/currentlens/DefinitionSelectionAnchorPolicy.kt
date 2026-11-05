package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset

/**
 * Converts a DOMRange.getBoundingClientRect() result (CSS viewport pixels) into
 * coordinates local to the Compose reader viewport. Both Android view and Compose
 * origins must be measured in the same window coordinate space.
 */
internal object DefinitionSelectionAnchorPolicy {
    fun fromCssRect(
        leftCssPx: Float,
        topCssPx: Float,
        widthCssPx: Float,
        heightCssPx: Float,
        webViewWindowOrigin: Offset,
        viewportWindowOrigin: Offset,
        cssToWindowScale: Float
    ): DefinitionSelectionAnchor? {
        val inputs = listOf(
            leftCssPx, topCssPx, widthCssPx, heightCssPx,
            webViewWindowOrigin.x, webViewWindowOrigin.y,
            viewportWindowOrigin.x, viewportWindowOrigin.y,
            cssToWindowScale
        )
        if (inputs.any { !it.isFinite() } ||
            widthCssPx <= 0f || heightCssPx <= 0f || cssToWindowScale <= 0f
        ) return null

        val widthPx = widthCssPx * cssToWindowScale
        val heightPx = heightCssPx * cssToWindowScale
        val center = Offset(
            webViewWindowOrigin.x + (leftCssPx + widthCssPx / 2f) * cssToWindowScale -
                viewportWindowOrigin.x,
            webViewWindowOrigin.y + (topCssPx + heightCssPx / 2f) * cssToWindowScale -
                viewportWindowOrigin.y
        )
        if (!center.x.isFinite() || !center.y.isFinite() ||
            !widthPx.isFinite() || !heightPx.isFinite() ||
            widthPx <= 0f || heightPx <= 0f
        ) return null

        return DefinitionSelectionAnchor(center, widthPx, heightPx)
    }
}
