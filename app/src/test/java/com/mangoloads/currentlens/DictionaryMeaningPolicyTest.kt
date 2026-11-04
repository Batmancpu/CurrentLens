package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryMeaningPolicyTest {
    @Test
    fun separatesPartOfSpeechFromDefinition() {
        assertEquals(
            DictionaryMeaningPresentation("noun", "an organism that feeds on organic matter"),
            DictionaryMeaningPolicy.present("noun · an organism that feeds on organic matter")
        )
    }

    @Test
    fun preservesAdditionalSeparatorsInsideDefinition() {
        assertEquals(
            DictionaryMeaningPresentation("verb", "to move · usually at speed"),
            DictionaryMeaningPolicy.present("verb · to move · usually at speed")
        )
    }

    @Test
    fun leavesUnlabelledDefinitionIntact() {
        assertEquals(
            DictionaryMeaningPresentation(null, "A definition without a POS label."),
            DictionaryMeaningPolicy.present("  A definition without a POS label.  ")
        )
    }

    @Test
    fun malformedPrefixIsNotDiscarded() {
        assertEquals(
            DictionaryMeaningPresentation(null, "noun ·"),
            DictionaryMeaningPolicy.present("noun · ")
        )
    }

    @Test
    fun copyTextIncludesUSPronunciationWhenAvailable() {
        assertEquals(
            "cat\n\nUS pronunciation: /ˈkæt/\n\nnoun · a small domesticated feline",
            DictionaryMeaningPolicy.copyText(
                word = "cat",
                meanings = listOf("noun · a small domesticated feline"),
                synonyms = emptyList(),
                pronunciation = "/ˈkæt/"
            )
        )
    }

    @Test
    fun copyTextIncludesHeadwordMeaningsAndDistinctRelatedWords() {
        assertEquals(
            "run\n\nverb · move quickly\n\nRelated: sprint · jog",
            DictionaryMeaningPolicy.copyText(
                word = " run ",
                meanings = listOf("verb · move quickly"),
                synonyms = listOf("sprint", "jog", "sprint", " ")
            )
        )
    }
}
