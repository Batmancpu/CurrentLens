package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class DefinitionGlassSurfacePolicyTest {
    @Test
    fun definitionPopupUsesTheSharedTransientOpticalTier() {
        assertEquals(22f, DefinitionGlassSurfacePolicy.CORNER_RADIUS_DP, 0.0001f)
        assertEquals(5f, DefinitionGlassSurfacePolicy.BLUR_RADIUS_DP, 0.0001f)
        assertEquals(2.5f, DefinitionGlassSurfacePolicy.REFRACTION_HEIGHT_PX, 0.0001f)
        assertEquals(3.5f, DefinitionGlassSurfacePolicy.REFRACTION_AMOUNT_PX, 0.0001f)
        assertEquals(0.001f, DefinitionGlassSurfacePolicy.CHROMATIC_ABERRATION, 0.0001f)
    }

    @Test
    fun tintUsesTheTransientScaleAndPreservesALegibilityFloor() {
        assertEquals(0.1344f, DefinitionGlassSurfacePolicy.tintAlpha(0.32f), 0.0001f)
        assertEquals(
            DefinitionGlassSurfacePolicy.MIN_TINT_ALPHA,
            DefinitionGlassSurfacePolicy.tintAlpha(0f),
            0.0001f
        )
        assertEquals(
            DefinitionGlassSurfacePolicy.MAX_TINT_ALPHA,
            DefinitionGlassSurfacePolicy.tintAlpha(1f),
            0.0001f
        )
    }

    @Test
    fun nonFiniteGlassAlphaFallsBackToTheSafeDefault() {
        val expected = DefinitionGlassSurfacePolicy.tintAlpha(0.32f)
        assertEquals(expected, DefinitionGlassSurfacePolicy.tintAlpha(Float.NaN), 0.0001f)
        assertEquals(expected, DefinitionGlassSurfacePolicy.tintAlpha(Float.POSITIVE_INFINITY), 0.0001f)
        assertEquals(expected, DefinitionGlassSurfacePolicy.tintAlpha(Float.NEGATIVE_INFINITY), 0.0001f)
    }
}
