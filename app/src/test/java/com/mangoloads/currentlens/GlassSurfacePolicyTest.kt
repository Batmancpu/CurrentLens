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
    fun opticalTintAndRimStaySubtleAndBoundedAtDefaultIntensity() {
        assertTrue(GlassSurfacePolicy.supportingTintAlpha(dark = true) in 0.10f..0.12f)
        assertTrue(GlassSurfacePolicy.supportingTintAlpha(dark = false) in 0.06f..0.08f)
        assertTrue(GlassSurfacePolicy.supportingRimAlpha(dark = true) in 0.18f..0.21f)
        assertTrue(GlassSurfacePolicy.supportingRimAlpha(dark = false) in 0.12f..0.14f)
        assertTrue(
            GlassSurfacePolicy.supportingTintAlpha(dark = true) >
                GlassSurfacePolicy.supportingTintAlpha(dark = false)
        )
    }

    @Test
    fun supportingTintAndRimTrackClearBalancedAndFrostedPreferences() {
        val clear = AppearancePreferencesPolicy.MIN_GLASS_ALPHA
        val balanced = AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA
        val frosted = AppearancePreferencesPolicy.MAX_GLASS_ALPHA

        assertEquals(0.09f, GlassSurfacePolicy.supportingTintAlpha(true, clear), 0.0001f)
        assertEquals(0.145f, GlassSurfacePolicy.supportingTintAlpha(true, frosted), 0.0001f)
        assertEquals(0.055f, GlassSurfacePolicy.supportingTintAlpha(false, clear), 0.0001f)
        assertEquals(0.095f, GlassSurfacePolicy.supportingTintAlpha(false, frosted), 0.0001f)
        assertEquals(0.17f, GlassSurfacePolicy.supportingRimAlpha(true, clear), 0.0001f)
        assertEquals(0.23f, GlassSurfacePolicy.supportingRimAlpha(true, frosted), 0.0001f)
        assertEquals(0.11f, GlassSurfacePolicy.supportingRimAlpha(false, clear), 0.0001f)
        assertEquals(0.16f, GlassSurfacePolicy.supportingRimAlpha(false, frosted), 0.0001f)

        assertTrue(GlassSurfacePolicy.supportingTintAlpha(true, clear) <
            GlassSurfacePolicy.supportingTintAlpha(true, balanced))
        assertTrue(GlassSurfacePolicy.supportingTintAlpha(true, balanced) <
            GlassSurfacePolicy.supportingTintAlpha(true, frosted))
    }

    @Test
    fun nonFiniteSupportingIntensityFallsBackToDefault() {
        assertEquals(
            GlassSurfacePolicy.supportingTintAlpha(true),
            GlassSurfacePolicy.supportingTintAlpha(true, Float.NaN),
            0f
        )
        assertEquals(
            GlassSurfacePolicy.supportingRimAlpha(false),
            GlassSurfacePolicy.supportingRimAlpha(false, Float.POSITIVE_INFINITY),
            0f
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

    @Test
    fun appearancePreviewControlsTrackClearFrostedAndReducedTransparency() {
        val clear = AppearancePreferencesPolicy.MIN_GLASS_ALPHA
        val balanced = AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA
        val frosted = AppearancePreferencesPolicy.MAX_GLASS_ALPHA

        assertEquals(0.38f, GlassSurfacePolicy.previewControlSurfaceAlpha(clear, false), 0.0001f)
        assertEquals(0.56f, GlassSurfacePolicy.previewControlSurfaceAlpha(frosted, false), 0.0001f)
        assertTrue(
            GlassSurfacePolicy.previewControlSurfaceAlpha(clear, false) <
                GlassSurfacePolicy.previewControlSurfaceAlpha(balanced, false)
        )
        assertTrue(
            GlassSurfacePolicy.previewControlSurfaceAlpha(balanced, false) <
                GlassSurfacePolicy.previewControlSurfaceAlpha(frosted, false)
        )
        assertEquals(0.62f, GlassSurfacePolicy.previewControlSurfaceAlpha(clear, true), 0.0001f)

        assertEquals(0.16f, GlassSurfacePolicy.previewControlRimAlpha(clear, false), 0.0001f)
        assertEquals(0.26f, GlassSurfacePolicy.previewControlRimAlpha(frosted, false), 0.0001f)
        assertEquals(0.28f, GlassSurfacePolicy.previewControlRimAlpha(clear, true), 0.0001f)

        assertEquals(0.10f, GlassSurfacePolicy.previewSelectedFillAlpha(clear, false), 0.0001f)
        assertEquals(0.22f, GlassSurfacePolicy.previewSelectedFillAlpha(frosted, false), 0.0001f)
        assertEquals(0.20f, GlassSurfacePolicy.previewSelectedFillAlpha(clear, true), 0.0001f)
    }

    @Test
    fun nonFinitePreviewIntensityUsesTheDefaultPreference() {
        assertEquals(
            GlassSurfacePolicy.previewControlSurfaceAlpha(
                AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA, false
            ),
            GlassSurfacePolicy.previewControlSurfaceAlpha(Float.NaN, false),
            0.0001f
        )
        assertEquals(
            GlassSurfacePolicy.previewControlRimAlpha(
                AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA, false
            ),
            GlassSurfacePolicy.previewControlRimAlpha(Float.POSITIVE_INFINITY, false),
            0.0001f
        )
    }

    @Test
    fun filterPillsTrackClearFrostedIntensityAndSelectionHierarchy() {
        val clear = AppearancePreferencesPolicy.MIN_GLASS_ALPHA
        val frosted = AppearancePreferencesPolicy.MAX_GLASS_ALPHA

        assertEquals(0.14f, GlassSurfacePolicy.filterPillFillAlpha(clear, true, false), 0.0001f)
        assertEquals(0.22f, GlassSurfacePolicy.filterPillFillAlpha(frosted, true, false), 0.0001f)
        assertEquals(0.04f, GlassSurfacePolicy.filterPillFillAlpha(clear, false, false), 0.0001f)
        assertEquals(0.06f, GlassSurfacePolicy.filterPillFillAlpha(frosted, false, false), 0.0001f)
        assertEquals(0.36f, GlassSurfacePolicy.filterPillBorderAlpha(clear, true, false), 0.0001f)
        assertEquals(0.46f, GlassSurfacePolicy.filterPillBorderAlpha(frosted, true, false), 0.0001f)
        assertEquals(0.18f, GlassSurfacePolicy.filterPillBorderAlpha(clear, false, false), 0.0001f)
        assertEquals(0.24f, GlassSurfacePolicy.filterPillBorderAlpha(frosted, false, false), 0.0001f)

        assertTrue(GlassSurfacePolicy.filterPillFillAlpha(clear, true, false) >
            GlassSurfacePolicy.filterPillFillAlpha(clear, false, false))
        assertTrue(GlassSurfacePolicy.filterPillBorderAlpha(clear, true, false) >
            GlassSurfacePolicy.filterPillBorderAlpha(clear, false, false))
    }

    @Test
    fun filterPillsUseHigherContrastStaticFallbackWhenTransparencyIsReduced() {
        val clear = AppearancePreferencesPolicy.MIN_GLASS_ALPHA
        assertEquals(0.24f, GlassSurfacePolicy.filterPillFillAlpha(clear, true, true), 0.0001f)
        assertEquals(0.10f, GlassSurfacePolicy.filterPillFillAlpha(clear, false, true), 0.0001f)
        assertEquals(0.48f, GlassSurfacePolicy.filterPillBorderAlpha(clear, true, true), 0.0001f)
        assertEquals(0.30f, GlassSurfacePolicy.filterPillBorderAlpha(clear, false, true), 0.0001f)
        assertEquals(16f, GlassSurfacePolicy.FILTER_PILL_CORNER_RADIUS_DP, 0f)
    }

    @Test
    fun nonFiniteFilterPillIntensityUsesTheDefaultPreference() {
        assertEquals(
            GlassSurfacePolicy.filterPillFillAlpha(
                AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA, true, false
            ),
            GlassSurfacePolicy.filterPillFillAlpha(Float.NaN, true, false),
            0.0001f
        )
        assertEquals(
            GlassSurfacePolicy.filterPillBorderAlpha(
                AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA, false, false
            ),
            GlassSurfacePolicy.filterPillBorderAlpha(Float.POSITIVE_INFINITY, false, false),
            0.0001f
        )
    }

}
