package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchMorphGeometryPolicyTest {
    @Test
    fun positionStartsAtSearchTabAndEndsAtMeasuredImeDock() {
        val start = SearchMorphGeometryPolicy.frame(
            sourceLeftPx = 24f, sourceTopPx = 600f,
            sourceWidthPx = 84f, sourceHeightPx = 56f,
            dockLeftPx = 16f, dockTopPx = 320f,
            dockWidthPx = 328f, dockHeightPx = 52f,
            progress = 0f
        )
        val end = SearchMorphGeometryPolicy.frame(
            sourceLeftPx = 24f, sourceTopPx = 600f,
            sourceWidthPx = 84f, sourceHeightPx = 56f,
            dockLeftPx = 16f, dockTopPx = 320f,
            dockWidthPx = 328f, dockHeightPx = 52f,
            progress = 1f
        )

        assertEquals(84f, start.widthPx, 0.001f)
        assertEquals(56f, start.heightPx, 0.001f)
        assertEquals(8f, start.offsetXPx, 0.001f)
        assertEquals(280f, start.offsetYPx, 0.001f)
        assertEquals(328f, end.widthPx, 0.001f)
        assertEquals(52f, end.heightPx, 0.001f)
        assertEquals(0f, end.offsetXPx, 0.001f)
        assertEquals(0f, end.offsetYPx, 0.001f)
    }

    @Test
    fun interpolationClampsProgressOutsideAnimationRange() {
        assertEquals(24f, SearchMorphGeometryPolicy.interpolate(24f, 320f, -1f), 0.001f)
        assertEquals(320f, SearchMorphGeometryPolicy.interpolate(24f, 320f, 2f), 0.001f)
        assertEquals(24f, SearchMorphGeometryPolicy.interpolate(24f, 320f, Float.NaN), 0.001f)
    }

    @Test
    fun capsuleEndCapsRemainCircularAtEveryMeasuredSize() {
        val start = SearchMorphGeometryPolicy.frame(0f, 0f, 84f, 56f, 0f, 0f, 328f, 52f, 0f)
        val middle = SearchMorphGeometryPolicy.frame(0f, 0f, 84f, 56f, 0f, 0f, 328f, 52f, 0.5f)
        val end = SearchMorphGeometryPolicy.frame(0f, 0f, 84f, 56f, 0f, 0f, 328f, 52f, 1f)

        assertEquals(28f, start.cornerRadiusPx, 0.001f)
        assertEquals(27f, middle.cornerRadiusPx, 0.001f)
        assertEquals(26f, end.cornerRadiusPx, 0.001f)
        assertTrue(start.cornerRadiusPx > 6f)
    }

    @Test
    fun tabAndSearchSurfacesCrossFadeAsComplementaryPlanes() {
        listOf(0f, 0.06f, 0.12f, 0.18f, 0.5f, 1f).forEach { progress ->
            val frame = SearchMorphGeometryPolicy.frame(0f, 0f, 84f, 56f, 0f, 0f, 328f, 52f, progress)
            assertEquals(1f, frame.tabBarAlpha + frame.fieldAlpha, 0.001f)
        }
        assertEquals(0f, SearchMorphGeometryPolicy.fieldAlpha(0f), 0.001f)
        assertEquals(0.5f, SearchMorphGeometryPolicy.fieldAlpha(0.09f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.fieldAlpha(0.18f), 0.001f)
    }

    @Test
    fun searchGlyphStartsCentredAndMovesToTheCapsuleLeadingInset() {
        val initialOffset = SearchMorphGeometryPolicy.collapsedIconOffsetPx(
            sourceWidthPx = 84f, iconWidthPx = 20f, leadingInsetPx = 13f
        )

        assertEquals(19f, initialOffset, 0.001f)
        assertEquals(19f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 0f), 0.001f)
        assertEquals(9.5f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 0.5f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 1f), 0.001f)
    }

    @Test
    fun reduceMotionUsesTheTargetGeometryImmediately() {
        assertEquals(1f, SearchMorphGeometryPolicy.effectiveProgress(0.32f, true, true), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.effectiveProgress(0.68f, false, true), 0.001f)
        assertEquals(0.32f, SearchMorphGeometryPolicy.effectiveProgress(0.32f, true, false), 0.001f)
    }

    @Test
    fun invalidAnimationProgressFallsBackToCollapsedGeometry() {
        assertEquals(0f, SearchMorphGeometryPolicy.effectiveProgress(Float.NaN, true, false), 0.001f)
        assertTrue(SearchMorphGeometryPolicy.iconOffset(Float.NaN, 0.5f).isFinite())
        val invalid = SearchMorphGeometryPolicy.frame(
            Float.NaN, Float.NaN, Float.NaN, Float.NaN,
            16f, 20f, 328f, 52f, Float.NaN
        )
        assertEquals(328f, invalid.widthPx, 0.001f)
        assertEquals(52f, invalid.heightPx, 0.001f)
        assertEquals(0f, invalid.offsetXPx, 0.001f)
        assertEquals(0f, invalid.offsetYPx, 0.001f)
    }
}
