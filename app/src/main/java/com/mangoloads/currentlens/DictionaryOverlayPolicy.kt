package com.mangoloads.currentlens

internal enum class DefinitionOverlayStatus {
    LOADING, LOOKUP_FAILED, DICTIONARY_MISSING, NO_ENTRY, AVAILABLE
}

/** Shared sizing, content visibility and gesture rules for both definition surfaces. */
internal object DictionaryOverlayPolicy {
    const val MAX_WIDTH_DP = 320
    const val COMPACT_MAX_HEIGHT_DP = 116
    const val EXPANDED_MAX_HEIGHT_DP = 420
    const val COLLAPSED_MEANING_MAX_LINES = 2

    fun visibleMeanings(total: Int, expanded: Boolean): Int =
        total.coerceAtLeast(0).coerceAtMost(if (expanded) 5 else 1)

    fun visibleSynonyms(total: Int, expanded: Boolean): Int =
        if (expanded) total.coerceAtLeast(0).coerceAtMost(8) else 0

    fun shouldShowRelatedWords(expanded: Boolean, total: Int): Boolean = expanded && total > 0
    fun shouldShowAttribution(expanded: Boolean, dictionaryAvailable: Boolean): Boolean =
        expanded && dictionaryAvailable
    fun shouldShowCopy(expanded: Boolean, hasCopyableContent: Boolean): Boolean =
        expanded && hasCopyableContent

    fun status(loading: Boolean, definition: Definition?): DefinitionOverlayStatus = when {
        loading -> DefinitionOverlayStatus.LOADING
        definition == null -> DefinitionOverlayStatus.LOOKUP_FAILED
        !definition.dictionaryAvailable -> DefinitionOverlayStatus.DICTIONARY_MISSING
        definition.meanings.isEmpty() -> DefinitionOverlayStatus.NO_ENTRY
        else -> DefinitionOverlayStatus.AVAILABLE
    }

    fun shouldExpand(dragDistancePx: Float, thresholdPx: Float): Boolean =
        dragDistancePx.isFinite() && thresholdPx.isFinite() &&
            thresholdPx > 0f && dragDistancePx <= -thresholdPx

    fun shouldCollapse(dragDistancePx: Float, thresholdPx: Float, scrollAtTop: Boolean): Boolean =
        scrollAtTop && dragDistancePx.isFinite() && thresholdPx.isFinite() &&
            thresholdPx > 0f && dragDistancePx >= thresholdPx
}
