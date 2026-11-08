package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedCardGeometryPolicyTest {
    @Test
    fun loadingAndArticleImagesShareAResponsiveSixteenByNineRatio() {
        assertEquals(16f / 9f, FeedCardGeometryPolicy.IMAGE_ASPECT_RATIO, 0.0001f)
    }

    @Test
    fun loadingAndArticleCardsShareContinuousGeometryAndContentRhythm() {
        assertEquals(29f, FeedCardGeometryPolicy.CARD_CORNER_RADIUS_DP, 0f)
        assertEquals(0.75f, FeedCardGeometryPolicy.CARD_RIM_WIDTH_DP, 0f)
        assertTrue(FeedCardGeometryPolicy.CARD_RIM_WIDTH_DP in 0.5f..1f)
        assertEquals(23f, FeedCardGeometryPolicy.IMAGE_CORNER_RADIUS_DP, 0f)
        assertEquals(3f, FeedCardGeometryPolicy.CARD_VERTICAL_INSET_DP, 0f)
        assertEquals(15f, FeedCardGeometryPolicy.CONTENT_HORIZONTAL_PADDING_DP, 0f)
        assertEquals(14f, FeedCardGeometryPolicy.CONTENT_VERTICAL_PADDING_DP, 0f)
        assertEquals(9f, FeedCardGeometryPolicy.CONTENT_VERTICAL_SPACING_DP, 0f)
        assertTrue(
            FeedCardGeometryPolicy.CONTENT_VERTICAL_SPACING_DP >
                FeedCardGeometryPolicy.TITLE_DESCRIPTION_SPACING_DP
        )
    }

    @Test
    fun skeletonReservesTheSameBookmarkTargetAndTextLineHeightsAsTheArticleCard() {
        assertEquals(
            TouchTargetPolicy.MIN_ICON_TARGET_DP.toFloat(),
            FeedCardGeometryPolicy.METADATA_ACTION_TARGET_DP,
            0f
        )
        assertEquals(16f, FeedCardGeometryPolicy.METADATA_TEXT_ROW_MIN_HEIGHT_DP, 0f)
        assertEquals(28f, FeedCardGeometryPolicy.TITLE_LINE_HEIGHT_SP, 0f)
        assertEquals(23f, FeedCardGeometryPolicy.DESCRIPTION_LINE_HEIGHT_SP, 0f)
    }
}
