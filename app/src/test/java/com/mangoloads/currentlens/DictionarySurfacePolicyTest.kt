package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionarySurfacePolicyTest {
    @Test
    fun reducedTransparencyDisablesBlurAndUsesOpaqueSurfaceOnModernAndroid() {
        val appearance = DictionarySurfacePolicy.resolve(
            reduceTransparency = true,
            supportsBlurBehind = true
        )

        assertFalse(appearance.blurBehindEnabled)
        assertEquals(0.985f, appearance.surfaceAlpha, 0f)
    }

    @Test
    fun normalAppearanceKeepsBlurBehindOnSupportedAndroid() {
        val appearance = DictionarySurfacePolicy.resolve(
            reduceTransparency = false,
            supportsBlurBehind = true
        )

        assertTrue(appearance.blurBehindEnabled)
        assertEquals(0.86f, appearance.surfaceAlpha, 0f)
    }

    @Test
    fun normalAppearanceKeepsExistingOpaqueFallbackOnOlderAndroid() {
        val appearance = DictionarySurfacePolicy.resolve(
            reduceTransparency = false,
            supportsBlurBehind = false
        )

        assertFalse(appearance.blurBehindEnabled)
        assertEquals(0.94f, appearance.surfaceAlpha, 0f)
    }

    @Test
    fun reducedTransparencyNeverEnablesBlurOnOlderAndroid() {
        val appearance = DictionarySurfacePolicy.resolve(
            reduceTransparency = true,
            supportsBlurBehind = false
        )

        assertFalse(appearance.blurBehindEnabled)
        assertEquals(0.985f, appearance.surfaceAlpha, 0f)
    }
}
