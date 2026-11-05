package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassOpticsPolicyTest {
    @Test
    fun selectedDropletKeepsItsLensAtRest() {
        val rest = GlassOpticsPolicy.droplet(0f)

        assertTrue(rest.refractionHeightDp > 0f)
        assertTrue(rest.refractionAmountDp > 0f)
        assertEquals(0.015f, rest.chromaticAberration, 0.0001f)
        assertTrue(rest.specularAlpha > 0f)
        assertTrue(rest.shadowAlpha > 0f)
    }

    @Test
    fun pressProgressDeepensTheLensAndEdgeLightingWithoutRainbowDispersion() {
        val rest = GlassOpticsPolicy.droplet(0f)
        val pressed = GlassOpticsPolicy.droplet(1f)

        assertTrue(pressed.refractionHeightDp > rest.refractionHeightDp)
        assertTrue(pressed.refractionAmountDp > rest.refractionAmountDp)
        assertTrue(pressed.specularAlpha > rest.specularAlpha)
        assertTrue(pressed.shadowAlpha > rest.shadowAlpha)
        assertTrue(pressed.insetAlpha > rest.insetAlpha)
        assertTrue(pressed.chromaticAberration <= 0.08f)
    }

    @Test
    fun opticalProgressIsClampedAndRejectsNonFiniteInput() {
        assertEquals(GlassOpticsPolicy.droplet(0f), GlassOpticsPolicy.droplet(-4f))
        assertEquals(GlassOpticsPolicy.droplet(1f), GlassOpticsPolicy.droplet(4f))
        assertEquals(GlassOpticsPolicy.droplet(0f), GlassOpticsPolicy.droplet(Float.NaN))
    }

    @Test
    fun searchGlassGainsOpticalDepthAsItsCapsuleExpands() {
        val collapsed = GlassOpticsPolicy.search(0f)
        val expanded = GlassOpticsPolicy.search(1f)

        assertTrue(expanded.blurDp > collapsed.blurDp)
        assertTrue(expanded.refractionHeightDp > collapsed.refractionHeightDp)
        assertTrue(expanded.refractionAmountDp > collapsed.refractionAmountDp)
        assertTrue(expanded.specularAlpha > collapsed.specularAlpha)
        assertTrue(expanded.shadowAlpha > collapsed.shadowAlpha)
        assertTrue(expanded.insetAlpha > collapsed.insetAlpha)
        assertTrue(collapsed.chromaticAberration <= 0.03f)
        assertTrue(expanded.chromaticAberration <= 0.03f)
    }

    @Test
    fun searchOpticsClampAndRejectNonFiniteMorphProgress() {
        assertEquals(GlassOpticsPolicy.search(0f), GlassOpticsPolicy.search(-4f))
        assertEquals(GlassOpticsPolicy.search(1f), GlassOpticsPolicy.search(4f))
        assertEquals(GlassOpticsPolicy.search(0f), GlassOpticsPolicy.search(Float.NaN))
    }

    @Test
    fun reduceMotionRemovesPressLiftAndInteractionOptics() {
        assertEquals(1f, GlassOpticsPolicy.pressedScale(true), 0.0001f)
        assertEquals(1.12f, GlassOpticsPolicy.pressedScale(false), 0.0001f)
        assertEquals(0f, GlassOpticsPolicy.interactionProgress(1f, true), 0.0001f)
        assertEquals(
            GlassOpticsPolicy.droplet(0f),
            GlassOpticsPolicy.droplet(1f, reduceMotion = true)
        )
    }

    @Test
    fun adaptiveTintAddsOnlyABoundedReserveAtLuminanceExtremes() {
        val middle = GlassOpticsPolicy.adaptiveTintAlpha(0.08f, 0.5f, 0.04f, 0.14f)
        val bright = GlassOpticsPolicy.adaptiveTintAlpha(0.08f, 1f, 0.04f, 0.14f)
        val dark = GlassOpticsPolicy.adaptiveTintAlpha(0.08f, 0f, 0.04f, 0.14f)

        assertEquals(0.08f, middle, 0.0001f)
        assertTrue(bright > middle)
        assertEquals(bright, dark, 0.0001f)
        assertEquals(0.14f, GlassOpticsPolicy.adaptiveTintAlpha(1f, 1f, 0.04f, 0.14f), 0.0001f)
    }
}
