package com.mangoloads.currentlens

import com.styropyr0.prismal.PrismalGlassPipeline

/**
 * Startup renderer safety policy.
 *
 * A device-specific native crash was observed in the Skia shader/display-list path.
 * Standard still builds RenderEffect blur and offscreen compositing layers, so it does
 * not isolate that path. Force Legacy on API 33+ during this diagnostic build: this
 * disables both AGSL and RenderEffect, retaining only Prismal's sampled backdrop and
 * frosted overlay.
 */
internal object StartupRenderPolicy {
    fun overrideFor(apiLevel: Int): PrismalGlassPipeline? =
        if (apiLevel >= 33) PrismalGlassPipeline.Legacy else null
}
