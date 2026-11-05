package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleCardMetaPolicyTest {
    @Test
    fun showsOneConciseHintWhenClassifierHasMatchedTerms() {
        assertEquals(
            "Indicative · GS III · Internal Security",
            ArticleCardMetaPolicy.syllabusHint(
                gsPaper = "GS III",
                subject = "Internal Security",
                matchedTerms = listOf("security")
            )
        )
    }

    @Test
    fun hidesUnmappedAndNegativeClassifierStates() {
        assertNull(ArticleCardMetaPolicy.syllabusHint("Unmapped", "Unmapped", emptyList()))
        assertNull(ArticleCardMetaPolicy.syllabusHint("GS II", "Governance", emptyList()))
    }

    @Test
    fun hidesHintsWhenPaperOrSubjectIsMissing() {
        assertNull(ArticleCardMetaPolicy.syllabusHint(" ", "Governance", listOf("law")))
        assertNull(ArticleCardMetaPolicy.syllabusHint("GS II", "", listOf("law")))
    }
}
