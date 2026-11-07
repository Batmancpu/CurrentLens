package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GeographyEditorialCoverageTest {
    private fun article(title: String, description: String, section: String = "India") = Article(
        id = title,
        title = title,
        description = description,
        url = "https://example.test/" + title.hashCode(),
        source = "Test",
        section = section,
        published = "2026-11-03T10:00:00Z"
    )

    @Test
    fun previouslyMissedPhysicalGeographyStoryMapsToGsOneAndPassesEligibility() {
        val story = article(
            "Monsoon onset shifts across the Indo-Gangetic plain",
            "IMD tracks rainfall distribution and changing river basin flows."
        )

        val relevance = UpscEngine.classify(story)

        assertEquals("GS I", relevance.gsPaper)
        assertEquals("Geography", relevance.subject)
        assertTrue(relevance.matchedTerms.containsAll(listOf("monsoon", "rainfall distribution", "river basin")))
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun entertainmentNoiseStillOverridesIncidentalGeographyTerms() {
        val story = article(
            "Film trailer celebrates Himalayan glaciers",
            "The movie uses glacier retreat and river basin imagery in a fictional landscape.",
            "Entertainment"
        )

        assertEquals("Geography", UpscEngine.classify(story).subject)
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun volcanoesAreNotDroppedBetweenGeographyClassificationAndFeedEligibility() {
        val story = article(
            "Volcanoes erupt after renewed tectonic activity",
            "Scientists monitor ash plumes and changes around the volcanic region."
        )

        val relevance = UpscEngine.classify(story)

        assertEquals("GS I", relevance.gsPaper)
        assertEquals("Geography", relevance.subject)
        assertTrue(relevance.matchedTerms.contains("volcanoes"))
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun geographyCategoryAndSearchAreWiredToTheGsOneMapping() {
        val story = article(
            "Monsoon onset shifts across the Indo-Gangetic plain",
            "Rainfall distribution and river basin flows are changing."
        )

        assertTrue(ExploreFilters.matchesCategory(story, "Geography"))
        assertTrue(ExploreFilters.matches(story, "Geography", "GS1, geography"))
        assertFalse(ExploreFilters.matchesCategory(story, "Science"))
    }

    @Test
    fun cardExplainsTheIndicativeSyllabusConnection() {
        assertEquals(
            "Indicative · GS I · Geography\nSignals: monsoon, river basin\nSyllabus: Salient features of physical geography and distribution of key natural resources.",
            ArticleCardMetaPolicy.syllabusHint(
                gsPaper = "GS I",
                subject = "Geography",
                matchedTerms = listOf("monsoon", "river basin", "rainfall distribution"),
                syllabusLink = "Salient features of physical geography and distribution of key natural resources."
            )
        )
    }
}
