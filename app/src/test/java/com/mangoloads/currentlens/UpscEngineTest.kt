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
        assertTrue(result.matchedTerms.containsAll(listOf("rbi", "inflation", "monetary")))
        assertEquals("3 matched keywords", result.signalLabel)
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
    }
}
