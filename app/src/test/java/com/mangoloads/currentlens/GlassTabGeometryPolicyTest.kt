package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassTabGeometryPolicyTest {
    @Test
    fun dropletBleedsBeyondTheTabCellButStaysCenteredOnItsTab() {
        val tabWidth = 80f
        val expandedWidth = GlassTabGeometryPolicy.expandedDimension(tabWidth, 12f)
        val center = GlassTabGeometryPolicy.tabCenterPx(4f, 2f, tabWidth)

        assertEquals(104f, expandedWidth, 0.001f)
        assertEquals(204f, center, 0.001f)
        assertEquals(152f, GlassTabGeometryPolicy.leftPx(center, expandedWidth), 0.001f)
    }

    @Test
    fun restingDropletScaleMatchesItsNormalTabFootprint() {
        val scaleX = GlassTabGeometryPolicy.restingScale(80f, 104f)
        val scaleY = GlassTabGeometryPolicy.restingScale(56f, 72f)

        assertEquals(80f, 104f * scaleX, 0.001f)
        assertEquals(56f, 72f * scaleY, 0.001f)
    }

    @Test
    fun dragVelocityShearsTheLensWithinSafeBounds() {
        val base = 0.8f
        assertEquals(
            base / (1f - 0.2f * 0.75f),
            GlassTabGeometryPolicy.horizontalScale(base, 1f, 2f),
            0.001f
        )
        assertEquals(
            base * (1f - 0.2f * 0.25f),
            GlassTabGeometryPolicy.verticalScale(base, 1f, 2f),
            0.001f
        )
        assertTrue(GlassTabGeometryPolicy.horizontalScale(base, 1f, 2f) > base)
    }

}
