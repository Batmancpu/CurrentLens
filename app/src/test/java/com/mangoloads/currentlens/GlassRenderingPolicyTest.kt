package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassRenderingPolicyTest {
    @Test
    fun normalModePreservesRequestedMaterialParameters() {
        assertEquals(8f, GlassRenderingPolicy.blurDp(8f, false), 0f)
        assertEquals(14f, GlassRenderingPolicy.refractionDp(14f, false), 0f)
        assertEquals(20f, GlassRenderingPolicy.refractionPx(20f, false), 0f)
        assertEquals(0.015f, GlassRenderingPolicy.chromaticAberration(0.015f, false), 0f)
        assertTrue(GlassRenderingPolicy.depthEnabled(true, false))
        assertEquals(0.4f, GlassRenderingPolicy.specularAlpha(0.4f, false), 0f)
        assertEquals(0.2f, GlassRenderingPolicy.shadowAlpha(0.2f, false), 0f)
        assertEquals(0.12f, GlassRenderingPolicy.tintAlpha(0.12f, false), 0f)
    }

    @Test
    fun reducedModeRemovesExpensiveOpticsAndDepth() {
        assertEquals(0f, GlassRenderingPolicy.blurDp(8f, true), 0f)
        assertEquals(0f, GlassRenderingPolicy.refractionDp(14f, true), 0f)
        assertEquals(0f, GlassRenderingPolicy.refractionPx(20f, true), 0f)
        assertEquals(0f, GlassRenderingPolicy.chromaticAberration(0.015f, true), 0f)
        assertFalse(GlassRenderingPolicy.depthEnabled(true, true))
        assertEquals(0f, GlassRenderingPolicy.shadowAlpha(0.2f, true), 0f)
        assertEquals(0.168f, GlassRenderingPolicy.specularAlpha(0.4f, true), 0.0001f)
    }

    @Test
    fun reducedModeRaisesOnlyLowTintAlpha() {
        assertEquals(
            GlassRenderingPolicy.REDUCED_MIN_TINT_ALPHA,
            GlassRenderingPolicy.tintAlpha(0.12f, true),
            0f
        )
        assertEquals(0.55f, GlassRenderingPolicy.tintAlpha(0.55f, true), 0f)
    }
}
