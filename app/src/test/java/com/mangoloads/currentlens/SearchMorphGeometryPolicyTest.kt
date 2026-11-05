package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchMorphGeometryPolicyTest {
    @Test
    fun positionStartsAtSearchTabAndEndsAtMeasuredImeDock() {
        assertEquals(24f, SearchMorphGeometryPolicy.interpolate(24f, 320f, 0f), 0.001f)
        assertEquals(320f, SearchMorphGeometryPolicy.interpolate(24f, 320f, 1f), 0.001f)
        assertEquals(172f, SearchMorphGeometryPolicy.interpolate(24f, 320f, 0.5f), 0.001f)
    }

    @Test
    fun interpolationClampsProgressOutsideAnimationRange() {
        assertEquals(24f, SearchMorphGeometryPolicy.interpolate(24f, 320f, -1f), 0.001f)
        assertEquals(320f, SearchMorphGeometryPolicy.interpolate(24f, 320f, 2f), 0.001f)
        assertEquals(24f, SearchMorphGeometryPolicy.interpolate(24f, 320f, Float.NaN), 0.001f)
    }

    @Test
    fun collapsedCapsuleMatchesTheSelectedTabsRestingFootprint() {
        val sourceTabWidth = 84f
        val dockWidth = 328f
        val tabContentHeight = 56f
        val dockHeight = 52f

        assertEquals(
            sourceTabWidth / dockWidth,
            SearchMorphGeometryPolicy.scaleFromAnchor(sourceTabWidth, dockWidth, 0f),
            0.001f
        )
        assertEquals(
            tabContentHeight / dockHeight,
            SearchMorphGeometryPolicy.scaleFromAnchor(tabContentHeight, dockHeight, 0f),
            0.001f
        )
        assertEquals(1f, SearchMorphGeometryPolicy.scaleFromAnchor(sourceTabWidth, dockWidth, 1f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.scaleFromAnchor(0f, dockWidth, 0f), 0.001f)
    }

    @Test
    fun searchGlyphStartsCentredAndMovesToTheCapsuleLeadingInset() {
        val initialOffset = SearchMorphGeometryPolicy.collapsedIconOffsetPx(
            sourceWidthPx = 84f,
            iconWidthPx = 20f,
            leadingInsetPx = 13f
        )

        assertEquals(19f, initialOffset, 0.001f)
        assertEquals(19f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 0f), 0.001f)
        assertEquals(9.5f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 0.5f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.iconOffset(initialOffset, 1f), 0.001f)
    }

    @Test
    fun tabPlaneAndSearchCapsuleCrossFadeAtTheSameAnchor() {
        assertEquals(1f, SearchMorphGeometryPolicy.tabBarAlpha(0f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.fieldAlpha(0f), 0.001f)
        assertEquals(2f / 3f, SearchMorphGeometryPolicy.tabBarAlpha(0.06f), 0.001f)
        assertEquals(0.5f, SearchMorphGeometryPolicy.fieldAlpha(0.06f), 0.001f)
        assertEquals(1f / 3f, SearchMorphGeometryPolicy.tabBarAlpha(0.12f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.fieldAlpha(0.12f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.tabBarAlpha(0.18f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.fieldAlpha(0.18f), 0.001f)
    }

    @Test
    fun refractionIntensifiesAsSearchCollapsesIntoTheTab() {
        assertEquals(0f, SearchMorphGeometryPolicy.collapsedRefractionFactor(1f), 0.001f)
        assertEquals(0.5f, SearchMorphGeometryPolicy.collapsedRefractionFactor(0.5f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.collapsedRefractionFactor(0f), 0.001f)
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
    }
}
