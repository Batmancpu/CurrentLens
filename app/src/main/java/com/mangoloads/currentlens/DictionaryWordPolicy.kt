package com.mangoloads.currentlens

import java.util.Locale

/** Pure, testable token and conservative morphology policy for offline dictionary lookups. */
internal object DictionaryWordPolicy {
    fun singleSelectedWord(raw: String): String? {
        val selected = raw.trim()
        if (selected.isEmpty() || selected.any { it.isWhitespace() }) return null
        val word = selected.trim { char ->
            !char.isLetter() && char != '\'' && char != '’' && char != '-'
        }
        if (word.isEmpty() || word.length > 60) return null
        if (word.any { !it.isLetter() && it != '\'' && it != '’' && it != '-' }) return null
        return word.lowercase(Locale.ROOT)
    }

    /** Exact form first; then conservative English inflections, with no network fallback. */
    fun candidates(word: String): List<String> {
        val base = word.lowercase(Locale.ROOT)
        val candidates = linkedSetOf(base)
        if (base.length > 4) {
            when {
                base.endsWith("ies") -> candidates.add(base.dropLast(3) + "y")
                base.endsWith("ing") -> {
                    val stem = base.dropLast(3)
                    candidates.add(stem)
                    if (stem.length > 2 && stem.last() == stem[stem.length - 2]) {
                        candidates.add(stem.dropLast(1))
                    } else {
                        candidates.add(stem + "e")
                    }
                }
                base.endsWith("ed") -> {
                    val stem = base.dropLast(2)
                    candidates.add(stem)
                    if (stem.length > 2 && stem.last() == stem[stem.length - 2]) {
                        candidates.add(stem.dropLast(1))
                    } else {
                        candidates.add(stem + "e")
                    }
                }
                base.endsWith("es") -> {
                    candidates.add(base.dropLast(2))
                    candidates.add(base.dropLast(1))
                }
                base.endsWith("s") && !base.endsWith("ss") -> candidates.add(base.dropLast(1))
            }
        }
        return candidates.filter { it.isNotBlank() }.take(6)
    }
}
