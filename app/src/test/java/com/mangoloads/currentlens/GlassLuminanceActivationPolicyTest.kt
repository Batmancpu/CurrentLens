package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GlassLuminanceActivationPolicyTest {
    @Test
    fun samplesOnlyOnForegroundIdleHomeSurfaces() {
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

    @Test
    fun pausesWhenReducedTransparencyIsEnabled() {
        assertFalse(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = true,
                readerOpen = false,
                settingsOpen = false,
                reduceTransparency = true
            )
        )
    }

    @Test
    fun pausesDuringFeedMotion() {
        assertFalse(
            GlassLuminanceActivationPolicy.shouldSample(
                isResumed = true,
                readerOpen = false,
                settingsOpen = false,
                feedScrolling = true
            )
        )
    }
}
