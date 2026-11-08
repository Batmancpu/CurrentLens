package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class AppearancePreferencesPolicyTest {
    @Test
    fun preservesValuesInsideSupportedRange() {
        assertEquals(0.28f, AppearancePreferencesPolicy.normalizeGlassAlpha(0.28f), 0f)
        assertEquals(0.32f, AppearancePreferencesPolicy.normalizeGlassAlpha(0.32f), 0f)
    }

    @Test
    fun clampsValuesOutsideSupportedRange() {
        assertEquals(0.12f, AppearancePreferencesPolicy.normalizeGlassAlpha(-1f), 0f)
        assertEquals(0.55f, AppearancePreferencesPolicy.normalizeGlassAlpha(1f), 0f)
    }

    @Test
    fun fallsBackForNonFiniteValues() {
        assertEquals(0.28f, AppearancePreferencesPolicy.normalizeGlassAlpha(Float.NaN), 0f)
        assertEquals(0.28f, AppearancePreferencesPolicy.normalizeGlassAlpha(Float.POSITIVE_INFINITY), 0f)
    }
}
