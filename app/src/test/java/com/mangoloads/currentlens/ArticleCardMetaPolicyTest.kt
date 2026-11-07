package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleCardMetaPolicyTest {
    @Test
    fun showsTheGsMappingAndConcreteMatchedSignals() {
        assertEquals(
            "Indicative · GS III · Indian Economy\nSignal: rbi, inflation",
            ArticleCardMetaPolicy.syllabusHint(
                gsPaper = "GS III",
                subject = "Indian Economy",
                matchedTerms = listOf("rbi", "inflation", "monetary"),
                syllabusLink = "Indian economy; mobilisation of resources, growth and development."
            )
        )
    }

    @Test
    fun hidesUnmappedAndNegativeClassifierStates() {
        assertNull(ArticleCardMetaPolicy.syllabusHint("Unmapped", "Unmapped", emptyList(), "No syllabus match"))
        assertNull(ArticleCardMetaPolicy.syllabusHint("GS II", "Governance", emptyList(), "Governance and accountability"))
    }

    @Test
    fun hidesHintsWhenPaperOrSubjectIsMissing() {
        assertNull(ArticleCardMetaPolicy.syllabusHint(" ", "Governance", listOf("law"), "Governance"))
        assertNull(ArticleCardMetaPolicy.syllabusHint("GS II", "", listOf("law"), "Governance"))
    }
}
