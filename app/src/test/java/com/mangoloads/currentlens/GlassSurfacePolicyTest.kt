package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassSurfacePolicyTest {
    @Test
    fun supportingContentRemainsNearlyOpaqueForReadability() {
        assertTrue(GlassSurfacePolicy.supportingSurfaceAlpha(dark = true) in 0.92f..0.95f)
        assertTrue(GlassSurfacePolicy.supportingSurfaceAlpha(dark = false) in 0.95f..0.98f)
    }

    @Test
    fun opticalTintAndRimStaySubtleAndBounded() {
        assertTrue(GlassSurfacePolicy.supportingTintAlpha(dark = true) in 0f..0.12f)
        assertTrue(GlassSurfacePolicy.supportingTintAlpha(dark = false) in 0f..0.08f)
        assertTrue(GlassSurfacePolicy.supportingRimAlpha(dark = true) in 0.18f..0.22f)
        assertTrue(GlassSurfacePolicy.supportingRimAlpha(dark = false) in 0.11f..0.15f)
        assertTrue(
            GlassSurfacePolicy.supportingTintAlpha(dark = true) >
                GlassSurfacePolicy.supportingTintAlpha(dark = false)
        )
    }

    @Test
    fun transientControlsUseOneBoundedLowCostMaterialTier() {
        assertTrue(GlassSurfacePolicy.TRANSIENT_BLUR_DP in 0f..6f)
        assertTrue(GlassSurfacePolicy.TRANSIENT_REFRACTION_HEIGHT_DP in 0f..3f)
        assertTrue(GlassSurfacePolicy.TRANSIENT_REFRACTION_AMOUNT_DP in 0f..4f)
        assertTrue(GlassSurfacePolicy.TRANSIENT_TINT_SCALE in 0f..0.5f)
    }

    @Test
    fun sharedGeometryAndMetricTypeScaleAreExplicit() {
        assertEquals(22f, GlassSurfacePolicy.SUPPORTING_CORNER_RADIUS_DP, 0f)
        assertEquals(20f, GlassSurfacePolicy.SUPPORTING_VALUE_SIZE_SP, 0f)
    }

    @Test
    fun supportingCardsShareConsistentReadableGeometry() {
        assertEquals(16f, SupportingCardGeometryPolicy.CONTENT_PADDING_DP, 0f)
        assertEquals(6f, SupportingCardGeometryPolicy.CONTENT_GAP_DP, 0f)
        assertEquals(12f, SupportingCardGeometryPolicy.SECTION_GAP_DP, 0f)
        assertEquals(9f, SupportingCardGeometryPolicy.METRIC_GAP_DP, 0f)
        assertTrue(SupportingCardGeometryPolicy.METRIC_MIN_HEIGHT_DP >= 48f)
        assertTrue(SupportingCardGeometryPolicy.DESTINATION_MIN_HEIGHT_DP >= 48f)
        assertTrue(SupportingCardGeometryPolicy.STAT_ROW_MIN_HEIGHT_DP >= 48f)
        assertTrue(SupportingCardGeometryPolicy.EMPTY_MIN_HEIGHT_DP >= 48f)
        assertTrue(
            SupportingCardGeometryPolicy.CONTENT_GAP_DP <
                SupportingCardGeometryPolicy.SECTION_GAP_DP
        )
    }
}
