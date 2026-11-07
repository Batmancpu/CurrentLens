package com.mangoloads.currentlens

/**
 * Shared article-card geometry keeps loading placeholders aligned with the real feed surface.
 * These are content-layout tokens, not a second glass or rendering pipeline.
 */
internal object FeedCardGeometryPolicy {
    const val IMAGE_ASPECT_RATIO = 16f / 9f
    const val CARD_CORNER_RADIUS_DP = 29f
    const val IMAGE_CORNER_RADIUS_DP = 23f
    const val CARD_VERTICAL_INSET_DP = 3f
    const val CONTENT_HORIZONTAL_PADDING_DP = 15f
    const val CONTENT_VERTICAL_PADDING_DP = 14f
    const val CONTENT_VERTICAL_SPACING_DP = 9f
    const val METADATA_ACTION_TARGET_DP = 48f
    const val METADATA_TEXT_ROW_MIN_HEIGHT_DP = 16f
    const val TITLE_DESCRIPTION_SPACING_DP = 6f
    const val TITLE_LINE_HEIGHT_SP = 28f
    const val DESCRIPTION_LINE_HEIGHT_SP = 23f
}
