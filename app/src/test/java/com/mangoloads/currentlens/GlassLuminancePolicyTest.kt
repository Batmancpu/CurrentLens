package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassLuminancePolicyTest {
    @Test
    fun convertsSrgbChannelsToLinearRelativeLuminance() {
        assertEquals(0f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFF000000.toInt())), 0.0001f)
        assertEquals(1f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFFFFFFFF.toInt())), 0.0001f)
        assertEquals(0.21586f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFF808080.toInt())), 0.001f)
    }

    @Test
    fun usesStandardRelativeLuminanceWeightsForPrimaryColours() {
        assertEquals(0.2126f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFFFF0000.toInt())), 0.0001f)
        assertEquals(0.7152f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFF00FF00.toInt())), 0.0001f)
        assertEquals(0.0722f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf(0xFF0000FF.toInt())), 0.0001f)
    }

    @Test
    fun selectsTheHigherContrastMonochromeForeground() {
        assertFalse(GlassLuminancePolicy.prefersDarkForeground(0.179f))
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(0.18f))
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(0.5f))
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(1f))
    }

    @Test
    fun clampsInvalidLuminanceAndUsesNeutralForEmptySamples() {
        assertEquals(0.5f, GlassLuminancePolicy.averageRelativeLuminance(intArrayOf()), 0.0001f)
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(Float.NaN))
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(Float.NEGATIVE_INFINITY))
        assertTrue(GlassLuminancePolicy.prefersDarkForeground(4f))
    }
}
