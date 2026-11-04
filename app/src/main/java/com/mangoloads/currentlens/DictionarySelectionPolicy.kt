package com.mangoloads.currentlens

/** Defers automatic word lookup long enough to preserve native multi-word selection. */
internal object DictionarySelectionPolicy {
    const val AUTO_DEFINE_DELAY_MS = 400L

    fun autoDefineWord(rawSelection: String, elapsedMillis: Long): String? {
        if (elapsedMillis < AUTO_DEFINE_DELAY_MS) return null
        return DictionaryWordPolicy.singleSelectedWord(rawSelection)
    }

    /** Rejects a delayed JavaScript selection result after its native ActionMode has ended. */
    fun manualDefineWord(
        rawSelection: String,
        requestGeneration: Int,
        currentGeneration: Int
    ): String? {
        if (requestGeneration != currentGeneration) return null
        return DictionaryWordPolicy.singleSelectedWord(rawSelection)
    }
}
