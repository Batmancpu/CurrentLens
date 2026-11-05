package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset
import kotlin.math.roundToInt

internal data class DefinitionSelectionAnchor(val center: Offset, val widthPx: Float, val heightPx: Float)

internal data class DefinitionPopupPlacement(
    val leftPx: Int, val topPx: Int, val widthPx: Int, val maxHeightPx: Int,
    val originX: Float, val originY: Float, val initialScaleX: Float, val initialScaleY: Float
)

/** Pure, viewport-aware placement for a word-definition popover anchored to a text selection. */
internal object DefinitionPopupPlacementPolicy {
    fun availableHeightOnSide(
        parentHeightPx: Int, anchorY: Float, marginPx: Int, gapPx: Int, preferAbove: Boolean
    ): Int {
        val margin = marginPx.coerceAtLeast(0)
        val gap = gapPx.coerceAtLeast(0)
        val y = anchorY.takeIf { it.isFinite() } ?: parentHeightPx / 2f
        val available = if (preferAbove) y - gap - margin
            else parentHeightPx - margin - y - gap
        return available.toInt().coerceAtLeast(1)
    }

    /** Resolve once from the compact footprint; callers retain this result while expanding. */
    fun preferAbove(
        parentHeightPx: Int, anchorY: Float, compactHeightPx: Int, marginPx: Int, gapPx: Int
    ): Boolean {
        val above = availableHeightOnSide(parentHeightPx, anchorY, marginPx, gapPx, true)
        val below = availableHeightOnSide(parentHeightPx, anchorY, marginPx, gapPx, false)
        val compactNeed = compactHeightPx.coerceAtLeast(1)
        return when {
            above >= compactNeed && below < compactNeed -> true
            below >= compactNeed && above < compactNeed -> false
            above > below -> true
            else -> false
        }
    }

    fun calculate(
        parentWidthPx: Int, parentHeightPx: Int, anchor: Offset, popupWidthPx: Int,
        estimatedHeightPx: Int, marginPx: Int, gapPx: Int,
        maxPopupWidthPx: Int = Int.MAX_VALUE, anchorWidthPx: Float = 0f,
        anchorHeightPx: Float = 0f, preferAbove: Boolean? = null
    ): DefinitionPopupPlacement {
        val margin = marginPx.coerceAtLeast(0)
        val widthLimit = (parentWidthPx - margin * 2).coerceAtLeast(1)
        val preferredWidth = popupWidthPx.coerceAtMost(maxPopupWidthPx.coerceAtLeast(1))
        val width = preferredWidth.coerceIn(1, widthLimit)
        val maxLeft = (parentWidthPx - width - margin).coerceAtLeast(margin)
        val x = anchor.x.takeIf { it.isFinite() } ?: parentWidthPx / 2f
        val y = anchor.y.takeIf { it.isFinite() } ?: parentHeightPx / 2f
        val left = (x - width / 2f).roundToInt().coerceIn(margin, maxLeft)
        val gap = gapPx.coerceAtLeast(0)
        val above = preferAbove ?: (y + gap + estimatedHeightPx.coerceAtLeast(1) > parentHeightPx - margin)
        // Auto-placement retains the prior whole-viewport sizing. Explicit side-locking
        // caps the viewport to that side, so expansion scrolls instead of jumping sides.
        val available = if (preferAbove == null) {
            (parentHeightPx - margin * 2).coerceAtLeast(1)
        } else {
            availableHeightOnSide(parentHeightPx, y, margin, gap, above)
        }
        val height = estimatedHeightPx.coerceAtLeast(1).coerceAtMost(available)
        val wantedTop = if (above) y - gap - height else y + gap
        val maxTop = (parentHeightPx - height - margin).coerceAtLeast(margin)
        val top = wantedTop.roundToInt().coerceIn(margin, maxTop)
        val originX = ((x - left) / width.toFloat()).coerceIn(0f, 1f)
        val originY = ((y - top) / height.toFloat()).coerceIn(0f, 1f)
        fun scale(anchorSize: Float, popupSize: Int): Float =
            if (anchorSize.isFinite() && anchorSize > 0f && popupSize > 0)
                (anchorSize / popupSize.toFloat()).coerceIn(0.035f, 0.85f) else 0.12f
        return DefinitionPopupPlacement(left, top, width, height, originX, originY,
            scale(anchorWidthPx, width), scale(anchorHeightPx, height))
    }
}
