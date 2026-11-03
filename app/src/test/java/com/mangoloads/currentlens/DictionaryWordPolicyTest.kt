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
}
