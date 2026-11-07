package com.mangoloads.currentlens

/** Prevents the shared glass sampler from running outside visible, idle home surfaces. */
internal object GlassLuminanceActivationPolicy {
    fun shouldSample(
        isResumed: Boolean,
        readerOpen: Boolean,
        settingsOpen: Boolean,
        feedScrolling: Boolean = false,
        reduceTransparency: Boolean = false
    ): Boolean = isResumed && !readerOpen && !settingsOpen && !feedScrolling && !reduceTransparency
}
