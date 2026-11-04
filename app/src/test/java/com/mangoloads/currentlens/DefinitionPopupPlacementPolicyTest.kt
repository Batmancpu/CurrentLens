package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DefinitionPopupPlacementPolicyTest {
    @Test
    fun opensBelowSelectionWhenThereIsRoom() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1080,
            parentHeightPx = 2000,
            anchor = Offset(540f, 220f),
            popupWidthPx = 1000,
            estimatedHeightPx = 520,
            marginPx = 32,
            gapPx = 24
        )
        assertEquals(244, result.topPx)
        assertEquals(0f, result.originY)
        assertEquals(1000, result.widthPx)
    }

    @Test
    fun opensAboveSelectionNearBottom() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1080,
            parentHeightPx = 2000,
            anchor = Offset(540f, 1900f),
            popupWidthPx = 1000,
            estimatedHeightPx = 520,
            marginPx = 32,
            gapPx = 24
        )
        assertEquals(1356, result.topPx)
        assertEquals(1f, result.originY)
    }

    @Test
    fun clampsHorizontallyAndRespectsViewportMargins() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1080,
            parentHeightPx = 900,
            anchor = Offset(2f, 400f),
            popupWidthPx = 1200,
            estimatedHeightPx = 420,
            marginPx = 24,
            gapPx = 18
        )
        assertEquals(24, result.leftPx)
        assertEquals(1032, result.widthPx)
        assertTrue(result.topPx >= 24)
        assertTrue(result.leftPx + result.widthPx <= 1056)
    }

    @Test
    fun keepsPopupInsideNarrowViewportAndUsesPositiveDimensions() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 320,
            parentHeightPx = 620,
            anchor = Offset(160f, 300f),
            popupWidthPx = 500,
            estimatedHeightPx = 560,
            marginPx = 12,
            gapPx = 10
        )
        assertEquals(296, result.widthPx)
        assertEquals(12, result.leftPx)
        assertTrue(result.topPx >= 12)
        assertTrue(result.originX in 0f..1f)
    }

    @Test
    fun handlesNonFiniteAnchorWithViewportCenter() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1000,
            parentHeightPx = 1000,
            anchor = Offset(Float.NaN, Float.NaN),
            popupWidthPx = 900,
            estimatedHeightPx = 300,
            marginPx = 20,
            gapPx = 16
        )
        assertTrue(result.leftPx >= 20)
        assertTrue(result.topPx >= 20)
        assertTrue(result.originX in 0f..1f)
    }
    @Test
    fun clampsPopoverWhenSelectionAnchorFallsAboveReaderViewport() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1080,
            parentHeightPx = 900,
            anchor = Offset(540f, -80f),
            popupWidthPx = 800,
            estimatedHeightPx = 300,
            marginPx = 24,
            gapPx = 18
        )
        assertEquals(24, result.topPx)
        assertEquals(140, result.leftPx)
        assertEquals(0f, result.originY)
        assertTrue(result.leftPx + result.widthPx <= 1056)
        assertTrue(result.topPx + 300 <= 876)
    }

    @Test
    fun capsPopoverHeightToCompactReaderViewport() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 360,
            parentHeightPx = 260,
            anchor = Offset(180f, 220f),
            popupWidthPx = 320,
            estimatedHeightPx = 300,
            marginPx = 12,
            gapPx = 8
        )

        assertEquals(236, result.maxHeightPx)
        assertEquals(12, result.topPx)
        assertTrue(result.topPx + result.maxHeightPx <= 248)
    }

}
