package com.mangoloads.currentlens

/** Compact-first presentation rules shared by the in-reader and PROCESS_TEXT definition cards. */
internal object DictionaryOverlayPolicy {
    fun visibleMeanings(total: Int, expanded: Boolean): Int =
        total.coerceAtLeast(0).coerceAtMost(if (expanded) 5 else 1)

    fun visibleSynonyms(total: Int, expanded: Boolean): Int =
        if (expanded) total.coerceAtLeast(0).coerceAtMost(8) else 0

    fun shouldExpand(dragDistancePx: Float, thresholdPx: Float): Boolean =
        dragDistancePx.isFinite() && thresholdPx.isFinite() &&
            thresholdPx > 0f && dragDistancePx <= -thresholdPx

    fun shouldCollapse(
        dragDistancePx: Float,
        thresholdPx: Float,
        scrollAtTop: Boolean
    ): Boolean =
        scrollAtTop && dragDistancePx.isFinite() && thresholdPx.isFinite() &&
            thresholdPx > 0f && dragDistancePx >= thresholdPx
}
