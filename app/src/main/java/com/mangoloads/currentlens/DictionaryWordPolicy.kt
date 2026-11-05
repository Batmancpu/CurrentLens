package com.mangoloads.currentlens

import java.text.Normalizer
import java.util.Locale

/** Pure, testable token and conservative morphology policy for offline dictionary lookups. */
internal object DictionaryWordPolicy {
    fun singleSelectedWord(raw: String): String? {
        val selected = normalizeTypography(Normalizer.normalize(raw.trim(), Normalizer.Form.NFC))
        if (selected.isEmpty() || selected.any { it.isWhitespace() }) return null
        // Strip punctuation around a selection, but preserve apostrophes and hyphens
        // inside real words such as "can't" and "state-of-the-art".
        val word = selected.trim { char -> !char.isLetter() }
        if (word.isEmpty() || word.length > 60) return null
        if (word.any { !it.isLetter() && it != '\'' && it != '’' && it != '-' }) return null
        return word.lowercase(Locale.ROOT)
    }

    /** Canonicalises typographic punctuation so copied publisher text matches dictionary keys. */
    private fun normalizeTypography(value: String): String = value
        .replace("\u2018", "'")
        .replace("\u2019", "'")
        .replace("\u02BC", "'")
        .replace("\uFF07", "'")
        .replace("\u2010", "-")
        .replace("\u2011", "-")
        .replace("\u2012", "-")
        .replace("\u2013", "-")
        .replace("\u2014", "-")
        .replace("\u2015", "-")
        .replace("\u2212", "-")
        .replace("\uFE63", "-")
        .replace("\uFF0D", "-")

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

    /**
     * Tries the exact token first, then dataset-authored WordNet exceptions, then
     * conservative suffix guesses. Exception lemmas are more reliable for ambiguous
     * forms such as "axes" and must not lose to a generic stem guess.
     */
    fun lookupCandidates(word: String, exceptionForms: List<String>): List<String> {
        val normalized = word.lowercase(Locale.ROOT)
        val exceptions = exceptionForms
            .map { it.trim().lowercase(Locale.ROOT) }
            .filter { it.isNotBlank() }
        return (listOf(normalized) + exceptions + candidates(normalized).drop(1))
            .distinct()
            .take(10)
    }
}
