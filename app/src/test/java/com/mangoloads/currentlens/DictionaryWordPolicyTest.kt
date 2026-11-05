package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DictionaryWordPolicyTest {
    @Test
    fun stripsSelectionPunctuationAndNormalizesCase() {
        assertEquals("commonwealth", DictionaryWordPolicy.singleSelectedWord("“Commonwealth.”"))
    }

    @Test
    fun stripsWrappingApostrophesAndEdgeHyphensButPreservesInternalWordPunctuation() {
        assertEquals("commonwealth", DictionaryWordPolicy.singleSelectedWord("'Commonwealth'"))
        assertEquals("state-of-the-art", DictionaryWordPolicy.singleSelectedWord("-State-of-the-Art-"))
        assertEquals("can't", DictionaryWordPolicy.singleSelectedWord("can't"))
    }

    @Test
    fun normalizesDecomposedAccentsAndPublisherTypography() {
        assertEquals("café", DictionaryWordPolicy.singleSelectedWord("cafe\u0301"))
        assertEquals("can't", DictionaryWordPolicy.singleSelectedWord("can\u2019t"))
        assertEquals(
            "state-of-the-art",
            DictionaryWordPolicy.singleSelectedWord("state\u2013of\u2011the\u2014art")
        )
    }

    @Test
    fun rejectsPunctuationOnlySelections() {
        listOf("-", "--", "'", "’", "—").forEach { selection ->
            assertNull(DictionaryWordPolicy.singleSelectedWord(selection))
        }
    }

    @Test
    fun rejectsMultiWordAndEmptySelections() {
        assertNull(DictionaryWordPolicy.singleSelectedWord("rule of law"))
        assertNull(DictionaryWordPolicy.singleSelectedWord("   "))
    }

    @Test
    fun proposesCommonInflectionCandidatesWithoutDroppingExactForm() {
        assertEquals(listOf("running", "runn", "run"), DictionaryWordPolicy.candidates("running"))
        assertEquals(listOf("cities", "city"), DictionaryWordPolicy.candidates("cities"))
        assertEquals(listOf("books", "book"), DictionaryWordPolicy.candidates("books"))
    }

    @Test
    fun preservesExactPhraseWordsWithApostrophesAndHyphens() {
        assertEquals("can't", DictionaryWordPolicy.singleSelectedWord("can't"))
        assertEquals("state-of-the-art", DictionaryWordPolicy.singleSelectedWord("State-of-the-Art"))
    }

    @Test
    fun prefersWordNetExceptionLemmasBeforeAmbiguousSuffixGuesses() {
        assertEquals(
            listOf("indices", "index", "indic", "indice"),
            DictionaryWordPolicy.lookupCandidates("indices", listOf("index", ""))
        )
        // Short ambiguous forms intentionally skip generic suffix guesses.
        assertEquals(
            listOf("axes", "axis", "axe"),
            DictionaryWordPolicy.lookupCandidates("axes", listOf("axis", "axe", ""))
        )
    }

    @Test
    fun keepsExactTokenFirstAndDeduplicatesExceptionAndFallbackCandidates() {
        assertEquals(
            listOf("running", "run", "runn"),
            DictionaryWordPolicy.lookupCandidates("running", listOf("run", "RUN", " "))
        )
    }
}
