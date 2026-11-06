package com.mangoloads.currentlens

import com.styropyr0.prismal.PrismalGlassPipeline
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class StartupRenderPolicyTest {
    @Test
    fun android13AndNewerUseLegacyRendererWithoutRenderEffectDuringStabilisation() {
        assertEquals(PrismalGlassPipeline.Legacy, StartupRenderPolicy.overrideFor(33))
        assertEquals(PrismalGlassPipeline.Legacy, StartupRenderPolicy.overrideFor(35))
    }

    @Test
    fun olderAndroidVersionsKeepTheirNativePipelineSelection() {
        assertNull(StartupRenderPolicy.overrideFor(32))
        assertNull(StartupRenderPolicy.overrideFor(26))
    }
}
