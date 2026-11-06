package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassLuminanceActivationPolicyTest {
    @Test
    fun samplesOnlyOnForegroundHomeSurfaces() {
        assertTrue(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = true,
                readerOpen = false,
                settingsOpen = false
            )
        )
    }

    @Test
    fun pausesWhenAppIsBackgrounded() {
        assertFalse(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = false,
                readerOpen = false,
                settingsOpen = false
            )
        )
    }

    @Test
    fun pausesWhileReaderOrSettingsIsVisible() {
        assertFalse(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = true,
                readerOpen = true,
                settingsOpen = false
            )
        )
        assertFalse(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = true,
                readerOpen = false,
                settingsOpen = true
            )
        )
    }
}
