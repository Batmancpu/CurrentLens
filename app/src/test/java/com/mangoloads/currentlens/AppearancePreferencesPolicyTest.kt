package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppearancePreferencesPolicyTest {
    @Test
    fun restoresGlassAlphaWithinTheSupportedRange() {
        assertEquals(0.12f, AppearancePreferencesPolicy.normalizeGlassAlpha(0.01f), 0.0001f)
        assertEquals(0.55f, AppearancePreferencesPolicy.normalizeGlassAlpha(0.9f), 0.0001f)
        assertEquals(0.34f, AppearancePreferencesPolicy.normalizeGlassAlpha(0.34f), 0.0001f)
    }

    @Test
    fun invalidGlassAlphaFallsBackToTheDefault() {
        assertEquals(
            AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA,
            AppearancePreferencesPolicy.normalizeGlassAlpha(Float.NaN),
            0.0001f
        )
        assertEquals(
            AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA,
            AppearancePreferencesPolicy.normalizeGlassAlpha(Float.POSITIVE_INFINITY),
            0.0001f
        )
    }

    @Test
    fun appearanceSnapshotPreservesReduceTransparency() {
        assertTrue(AppearancePreferencesPolicy.create(0.3f, true).reduceTransparency)
        assertFalse(AppearancePreferencesPolicy.create(0.3f, false).reduceTransparency)
    }
}
