package com.mangoloads.currentlens

import com.styropyr0.prismal.PrismalGlassPipeline
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class StartupRenderPolicyTest {
    @Test
    fun android13AndNewerUseStandardRendererDuringStabilisation() {
        assertEquals(PrismalGlassPipeline.Standard, StartupRenderPolicy.overrideFor(33))
        assertEquals(PrismalGlassPipeline.Standard, StartupRenderPolicy.overrideFor(35))
    }

    @Test
    fun olderAndroidVersionsKeepTheirNativePipelineSelection() {
        assertNull(StartupRenderPolicy.overrideFor(32))
        assertNull(StartupRenderPolicy.overrideFor(26))
    }
}
