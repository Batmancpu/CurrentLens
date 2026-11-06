package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpscEngineTest {
    @Test
    fun mappingExposesMatchedTermsInsteadOfAnUnvalidatedScore() {
        val article = Article(
            id = "rbi",
            title = "RBI holds repo rate amid inflation concerns",
            description = "The monetary policy committee reviewed the policy rate.",
            url = "https://example.test/rbi",
            source = "Test",
            section = "Economy",
            published = "2026-11-03T10:00:00Z"
        )

        val result = UpscEngine.classify(article)

        assertEquals("GS III", result.gsPaper)
        assertEquals("Indian Economy", result.subject)
        assertTrue(result.matchedTerms.containsAll(listOf("rbi", "repo rate", "inflation", "monetary")))
        assertEquals("4 matched keywords", result.signalLabel)
    }

    @Test
    fun unmatchedHeadlineIsNotForcedIntoAGsPaper() {
        val article = Article(
            id = "unmapped",
            title = "A quiet day in a village",
            description = "Residents prepare for a local gathering.",
            url = "https://example.test/unmapped",
            source = "Test",
            section = "Local",
            published = "2026-11-03T10:00:00Z"
        )

        val result = UpscEngine.classify(article)

        assertEquals("Unmapped", result.gsPaper)
        assertTrue(result.matchedTerms.isEmpty())
        assertEquals("No keyword match", result.signalLabel)
        assertEquals("No clear match", UpscEngine.questions(article, result)[1].options[
            UpscEngine.questions(article, result)[1].correctIndex
        ])
    }

    @Test
    fun rbiMustMatchAsAStandaloneTermRatherThanInsideAnUnrelatedWord() {
        val article = Article(
            id = "arbitrary",
            title = "Arbitrary changes to a local event schedule",
            description = "Residents prepare for a neighbourhood gathering.",
            url = "https://example.test/arbitrary",
            source = "Test",
            section = "Local",
            published = "2026-11-03T10:00:00Z"
        )

        assertEquals("Unmapped", UpscEngine.classify(article).gsPaper)
    }

    @Test
    fun mapsSpecificHealthAndDisasterThemesToTheirSyllabusPapers() {
        val health = Article(
            id = "health",
            title = "National Health Mission expands maternal health coverage",
            description = "The programme addresses public health outcomes.",
            url = "https://example.test/health",
            source = "Test",
            section = "India",
            published = "2026-11-03T10:00:00Z"
        )
        val disaster = Article(
            id = "disaster",
            title = "Cyclone early warning system upgraded along the coast",
            description = "The disaster management authority reviews preparedness.",
            url = "https://example.test/disaster",
            source = "Test",
            section = "India",
            published = "2026-11-03T10:00:00Z"
        )

        assertEquals("GS II", UpscEngine.classify(health).gsPaper)
        assertEquals("GS III", UpscEngine.classify(disaster).gsPaper)
    }

    @Test
    fun quizAlwaysContainsFiveQuestionsAndMarksAuthoredPromptsHonestly() {
        val article = Article(
            id = "general",
            title = "A quiet day in a village",
            description = "",
            url = "https://example.test/general",
            source = "Test",
            section = "Local",
            published = "2026-11-03T10:00:00Z"
        )
        val questions = UpscEngine.questions(article, UpscEngine.classify(article))

        assertEquals(5, questions.size)
        assertTrue(questions[2].provenance.contains("not an official UPSC question"))
        assertTrue(questions[4].provenance.contains("not an official UPSC question"))
        assertTrue(questions[0].provenance.contains("RSS headline only"))
        assertTrue(questions[1].provenance.contains("Rule-based headline and excerpt"))
        assertTrue(questions[2].provenance.contains("Original practice question"))
        assertTrue(questions[3].provenance.contains("Original practice question"))
        assertTrue(questions[4].provenance.contains("PYQ-style practice"))
    }
}
