package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class GlassLuminanceRegionPolicyTest {
    @Test
    fun bottomBandUsesFullWidthAndAnchorsToTheSourceBottom() {
        val region = GlassLuminanceRegionPolicy.bottomBand(
            sourceWidthPx = 1080,
            sourceHeightPx = 2400,
            requestedHeightPx = 312
        )

        assertNotNull(region)
        assertEquals(0, region!!.leftPx)
        assertEquals(2088, region.topPx)
        assertEquals(1080, region.widthPx)
        assertEquals(312, region.heightPx)
        assertEquals(2400, region.topPx + region.heightPx)
    }

    @Test
    fun requestedBandIsClampedToCompactSourceHeight() {
        val region = GlassLuminanceRegionPolicy.bottomBand(
            sourceWidthPx = 720,
            sourceHeightPx = 180,
            requestedHeightPx = 312
        )

        assertNotNull(region)
        assertEquals(0, region!!.topPx)
        assertEquals(720, region.widthPx)
        assertEquals(180, region.heightPx)
    }

    @Test
    fun invalidSourceOrBandDimensionsDoNotCreateAProbeRegion() {
        assertNull(GlassLuminanceRegionPolicy.bottomBand(0, 100, 20))
        assertNull(GlassLuminanceRegionPolicy.bottomBand(100, 0, 20))
        assertNull(GlassLuminanceRegionPolicy.bottomBand(100, 100, 0))
        assertNull(GlassLuminanceRegionPolicy.bottomBand(-1, 100, 20))
    }
}
