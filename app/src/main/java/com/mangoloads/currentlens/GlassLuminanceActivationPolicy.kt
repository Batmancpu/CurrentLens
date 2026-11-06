package com.mangoloads.currentlens

/** Prevents the shared glass sampler from running outside visible home surfaces. */
internal object GlassLuminanceActivationPolicy {
    fun shouldSample(
        isResumed: Boolean,
        readerOpen: Boolean,
        settingsOpen: Boolean
    ): Boolean = isResumed && !readerOpen && !settingsOpen
}
