package com.mangoloads.currentlens

/** Structured display data parsed from the POS-prefixed Open English WordNet gloss rows. */
internal data class DictionaryMeaningPresentation(
    val partOfSpeech: String?,
    val definition: String
)

/** Keeps WordNet metadata separate from its gloss in both reader and PROCESS_TEXT surfaces. */
internal object DictionaryMeaningPolicy {
    private const val SEPARATOR = " · "

    fun present(raw: String): DictionaryMeaningPresentation {
        val value = raw.trim()
        val separatorIndex = value.indexOf(SEPARATOR)
        if (separatorIndex <= 0 || separatorIndex + SEPARATOR.length >= value.length) {
            return DictionaryMeaningPresentation(partOfSpeech = null, definition = value)
        }

        val partOfSpeech = value.substring(0, separatorIndex).trim()
        val definition = value.substring(separatorIndex + SEPARATOR.length).trim()
        if (partOfSpeech.isEmpty() || definition.isEmpty()) {
            return DictionaryMeaningPresentation(partOfSpeech = null, definition = value)
        }
        return DictionaryMeaningPresentation(partOfSpeech, definition)
    }

    fun hasCopyableContent(
        meanings: List<String>,
        synonyms: List<String>,
        pronunciation: String?
    ): Boolean =
        pronunciation?.isNotBlank() == true ||
            meanings.any { present(it).definition.isNotBlank() } ||
            synonyms.any { it.isNotBlank() }

    fun copyText(
        word: String,
        meanings: List<String>,
        synonyms: List<String>,
        pronunciation: String? = null
    ): String = buildString {
        append(word.trim())
        pronunciation?.trim()?.takeIf { it.isNotEmpty() }?.let {
            append("\n\nUS pronunciation: ")
            append(it)
        }
        val formattedMeanings = meanings.map(::present).filter { it.definition.isNotBlank() }
        if (formattedMeanings.isNotEmpty()) {
            append("\n\n")
            append(formattedMeanings.joinToString("\n\n") { meaning ->
                meaning.partOfSpeech?.let { "$it$SEPARATOR" + meaning.definition } ?: meaning.definition
            })
        }
        val related = synonyms.map(String::trim).filter(String::isNotEmpty).distinct()
        if (related.isNotEmpty()) {
            append("\n\nRelated: ")
            append(related.joinToString(" · "))
        }
    }
}
