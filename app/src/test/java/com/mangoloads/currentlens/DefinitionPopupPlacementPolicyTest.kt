package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
    fun capsPopoverWidthOnWideReadersAndKeepsItsOriginAtTheSelection() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 1200,
            parentHeightPx = 1800,
            anchor = Offset(600f, 500f),
            popupWidthPx = 1176,
            estimatedHeightPx = 520,
            marginPx = 12,
            gapPx = 14,
            maxPopupWidthPx = 420
        )

        assertEquals(420, result.widthPx)
        assertEquals(390, result.leftPx)
        assertEquals(0.5f, result.originX)
        assertTrue(result.leftPx + result.widthPx <= 1188)
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

    @Test
    fun keepsMorphOriginAtSelectionWhenCompactViewportClampsPopoverPosition() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 360,
            parentHeightPx = 260,
            anchor = Offset(180f, 220f),
            popupWidthPx = 320,
            estimatedHeightPx = 300,
            marginPx = 12,
            gapPx = 8
        )

        assertEquals(12, result.topPx)
        assertEquals(236, result.maxHeightPx)
        assertEquals(208f / 236f, result.originY, 0.0001f)
    }


    @Test
    fun usesMeasuredPopupHeightAndSelectionBoundsForTheMorph() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 360,
            parentHeightPx = 1000,
            anchor = Offset(180f, 900f),
            popupWidthPx = 320,
            estimatedHeightPx = 180,
            marginPx = 12,
            gapPx = 10,
            anchorWidthPx = 48f,
            anchorHeightPx = 24f
        )

        assertEquals(710, result.topPx)
        assertEquals(1f, result.originY)
        assertEquals(0.15f, result.initialScaleX, 0.0001f)
        assertEquals(24f / 180f, result.initialScaleY, 0.0001f)
    }

    @Test
    fun usesSafeMorphScaleWhenSelectionBoundsAreMissingOrInvalid() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 360,
            parentHeightPx = 900,
            anchor = Offset(180f, 420f),
            popupWidthPx = 320,
            estimatedHeightPx = 240,
            marginPx = 12,
            gapPx = 10,
            anchorWidthPx = 0f,
            anchorHeightPx = Float.NaN
        )

        assertEquals(0.12f, result.initialScaleX)
        assertEquals(0.12f, result.initialScaleY)
    }


    @Test
    fun choosesAboveNearBottomAndBelowNearTopFromCompactFootprint() {
        assertTrue(DefinitionPopupPlacementPolicy.preferAbove(1000, 850f, 116, 12, 14))
        assertFalse(DefinitionPopupPlacementPolicy.preferAbove(1000, 150f, 116, 12, 14))
    }

    @Test
    fun keepsExpandedPopoverOnTheChosenSideAndCapsItsScrollViewport() {
        val result = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = 400, parentHeightPx = 500, anchor = Offset(200f, 220f),
            popupWidthPx = 320, estimatedHeightPx = 420, marginPx = 12, gapPx = 14,
            maxPopupWidthPx = 320, preferAbove = true
        )
        assertEquals(320, result.widthPx)
        assertEquals(194, result.maxHeightPx)
        assertEquals(12, result.topPx)
        assertTrue(result.topPx + result.maxHeightPx <= 208)
    }

    @Test
    fun availableHeightNeverExceedsTheLockedSideOfTheViewport() {
        val above = DefinitionPopupPlacementPolicy.availableHeightOnSide(600, 250f, 12, 14, true)
        val below = DefinitionPopupPlacementPolicy.availableHeightOnSide(600, 250f, 12, 14, false)
        assertEquals(224, above)
        assertEquals(324, below)
        assertTrue(above > 0)
        assertTrue(below > 0)
    }

}
