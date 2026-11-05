package com.mangoloads.currentlens

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DefinitionSelectionAnchorPolicyTest {
    @Test
    fun convertsDomRectIntoReaderViewportUsingOneWindowCoordinateSpace() {
        val anchor = DefinitionSelectionAnchorPolicy.fromCssRect(
            leftCssPx = 10f,
            topCssPx = 20f,
            widthCssPx = 40f,
            heightCssPx = 12f,
            webViewWindowOrigin = Offset(22f, 104f),
            viewportWindowOrigin = Offset(12f, 80f),
            cssToWindowScale = 2.5f
        )

        requireNotNull(anchor)
        assertEquals(85f, anchor.center.x, 0.0001f)
        assertEquals(89f, anchor.center.y, 0.0001f)
        assertEquals(100f, anchor.widthPx, 0.0001f)
        assertEquals(30f, anchor.heightPx, 0.0001f)
    }

    @Test
    fun preservesSelectionPartlyOutsideViewportForPlacementPolicyToClamp() {
        val anchor = DefinitionSelectionAnchorPolicy.fromCssRect(
            leftCssPx = 4f,
            topCssPx = -30f,
            widthCssPx = 20f,
            heightCssPx = 10f,
            webViewWindowOrigin = Offset(0f, 100f),
            viewportWindowOrigin = Offset(0f, 100f),
            cssToWindowScale = 3f
        )

        requireNotNull(anchor)
        assertEquals(-75f, anchor.center.y, 0.0001f)
    }

    @Test
    fun rejectsInvalidRectOriginsAndScale() {
        val invalid = listOf(
            arrayOf(0f, 0f, 0f, 10f, 1f),
            arrayOf(0f, 0f, 10f, -1f, 1f),
            arrayOf(Float.NaN, 0f, 10f, 10f, 1f),
            arrayOf(0f, 0f, 10f, 10f, 0f),
            arrayOf(0f, 0f, 10f, 10f, Float.POSITIVE_INFINITY)
        )
        invalid.forEach { values ->
            assertNull(
                DefinitionSelectionAnchorPolicy.fromCssRect(
                    leftCssPx = values[0],
                    topCssPx = values[1],
                    widthCssPx = values[2],
                    heightCssPx = values[3],
                    webViewWindowOrigin = Offset.Zero,
                    viewportWindowOrigin = Offset.Zero,
                    cssToWindowScale = values[4]
                )
            )
        }
    }

    @Test
    fun rejectsNonFiniteWindowOriginsAndOverflowedPhysicalBounds() {
        assertNull(
            DefinitionSelectionAnchorPolicy.fromCssRect(
                leftCssPx = 0f,
                topCssPx = 0f,
                widthCssPx = 10f,
                heightCssPx = 10f,
                webViewWindowOrigin = Offset(Float.NaN, 0f),
                viewportWindowOrigin = Offset.Zero,
                cssToWindowScale = 1f
            )
        )
        assertNull(
            DefinitionSelectionAnchorPolicy.fromCssRect(
                leftCssPx = 0f,
                topCssPx = 0f,
                widthCssPx = Float.MAX_VALUE,
                heightCssPx = 10f,
                webViewWindowOrigin = Offset.Zero,
                viewportWindowOrigin = Offset.Zero,
                cssToWindowScale = 4f
            )
        )
    }
}
