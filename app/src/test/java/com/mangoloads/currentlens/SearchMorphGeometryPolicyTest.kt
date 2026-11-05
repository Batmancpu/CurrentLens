package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
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
    }

    @Test
    fun refractionIntensifiesAsSearchCollapsesIntoTheTab() {
        assertEquals(0f, SearchMorphGeometryPolicy.collapsedRefractionFactor(1f), 0.001f)
        assertEquals(0.5f, SearchMorphGeometryPolicy.collapsedRefractionFactor(0.5f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.collapsedRefractionFactor(0f), 0.001f)
    }

    @Test
    fun tabAndComposerCrossFadeAtTheSameAnchor() {
        assertEquals(1f, SearchMorphGeometryPolicy.tabBarAlpha(0f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.tabBarAlpha(1f), 0.001f)
        assertEquals(0f, SearchMorphGeometryPolicy.fieldAlpha(0f), 0.001f)
        assertEquals(1f, SearchMorphGeometryPolicy.fieldAlpha(1f), 0.001f)
    }
}
