package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset
import kotlin.math.roundToInt

internal data class DefinitionPopupPlacement(
    val leftPx: Int,
    val topPx: Int,
    val widthPx: Int,
    val maxHeightPx: Int,
    val originX: Float,
    val originY: Float
)

/** Pure, viewport-aware placement for a word-definition popover anchored to a text selection. */
internal object DefinitionPopupPlacementPolicy {
    fun calculate(
        parentWidthPx: Int,
        parentHeightPx: Int,
        anchor: Offset,
        popupWidthPx: Int,
        estimatedHeightPx: Int,
        marginPx: Int,
        gapPx: Int,
        maxPopupWidthPx: Int = Int.MAX_VALUE
    ): DefinitionPopupPlacement {
        val safeMargin = marginPx.coerceAtLeast(0)
        val widthLimit = (parentWidthPx - safeMargin * 2).coerceAtLeast(1)
        val preferredWidth = popupWidthPx.coerceAtMost(maxPopupWidthPx.coerceAtLeast(1))
        val width = preferredWidth.coerceIn(1, widthLimit)
        val maxLeft = (parentWidthPx - width - safeMargin).coerceAtLeast(safeMargin)
        val anchorX = anchor.x.takeIf { it.isFinite() } ?: parentWidthPx / 2f
        val anchorY = anchor.y.takeIf { it.isFinite() } ?: parentHeightPx / 2f
        val left = (anchorX - width / 2f).roundToInt().coerceIn(safeMargin, maxLeft)

        val safeGap = gapPx.coerceAtLeast(0)
        // In landscape, split-screen or a compact window, a fixed 300 dp cap can
        // exceed the reader viewport and clip the definition. Bound the surface to
        // the actual available height; its content remains vertically scrollable.
        val availableHeight = (parentHeightPx - safeMargin * 2).coerceAtLeast(1)
        val safeHeight = estimatedHeightPx.coerceAtLeast(1).coerceAtMost(availableHeight)
        val showAbove = anchorY + safeGap + safeHeight > parentHeightPx - safeMargin
        val desiredTop = if (showAbove) anchorY - safeGap - safeHeight else anchorY + safeGap
        val maxTop = (parentHeightPx - safeHeight - safeMargin).coerceAtLeast(safeMargin)
        val top = desiredTop.roundToInt().coerceIn(safeMargin, maxTop)
        val originX = ((anchorX - left) / width.toFloat()).coerceIn(0f, 1f)
        val originY = if (showAbove) 1f else 0f

        return DefinitionPopupPlacement(left, top, width, safeHeight, originX, originY)
    }
}
