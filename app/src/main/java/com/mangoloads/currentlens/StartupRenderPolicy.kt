package com.mangoloads.currentlens

import com.styropyr0.prismal.PrismalGlassPipeline

/**
 * Startup renderer safety policy.
 *
 * API 33+ devices default to Prismal's Liquid/AGSL tier. During launch stabilisation,
 * force Standard so blur remains available through RenderEffect but AGSL shaders are
 * not selected or initialised. Older devices retain their normal automatic tier.
 */
internal object StartupRenderPolicy {
    fun overrideFor(apiLevel: Int): PrismalGlassPipeline? =
        if (apiLevel >= 33) PrismalGlassPipeline.Standard else null
}
