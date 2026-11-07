package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Synthetic editorial fixtures only; these are not verbatim news stories or UPSC PYQs.
 * They guard both missed syllabus topics and entertainment/viral false positives.
 */
class EditorialConsolidationRegressionTest {
    private fun article(title: String, description: String = "", section: String = "India") = Article(
        id = title,
        title = title,
        description = description,
        url = "https://example.test/" + title.hashCode(),
        source = "Test",
        section = section,
        published = "2026-11-06T10:00:00Z"
    )

    @Test
    fun physicalGeographySignalsMapAndEnterEditorialFeed() {
        val story = article("Monsoon onset shifts across the river basin", "Rainfall distribution changes across the region")
        val relevance = UpscEngine.classify(story)
        assertEquals("GS I", relevance.gsPaper)
        assertEquals("Geography", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
        assertTrue(ExploreFilters.matchesCategory(story, "Geography"))
    }

    @Test
    fun thinFilmPhotovoltaicTechnologyIsRetainedDespiteFilmNoiseTerm() {
        val story = article("Thin-film photovoltaic solar cells improve conversion efficiency")
        val relevance = UpscEngine.classify(story)
        assertEquals("GS III", relevance.gsPaper)
        assertEquals("Science & Technology", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun genericFilmPromotionWithIncidentalClimateTermsIsSuppressed() {
        val story = article("New film explores climate change in the Himalayas")
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun viralChallengeWithIncidentalBiodiversityTermsIsSuppressed() {
        val story = article("Viral challenge trends online", "The clip uses biodiversity and forest imagery")
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun routineSportsScoreIsSuppressed() {
        val story = article("India cricket final score and match highlights", "Full scorecard and league table")
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun financeBillMapsToGsThreeEconomy() {
        val story = article("Finance Bill changes direct tax provisions")
        val relevance = UpscEngine.classify(story)
        assertEquals("GS III", relevance.gsPaper)
        assertEquals("Indian Economy", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun namedPersonWithBillInNameDoesNotMapToConstitution() {
        val story = article("Bill Gates visits a local community", "A philanthropic foundation event")
        assertEquals("Unmapped", UpscEngine.classify(story).gsPaper)
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun specificParliamentaryBillPhraseMapsToConstitution() {
        val story = article("Bill introduced in Lok Sabha to amend a constitutional provision")
        val relevance = UpscEngine.classify(story)
        assertEquals("GS II", relevance.gsPaper)
        assertEquals("Constitution", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun parliamentFacilityMentionAloneIsRejected() {
        val story = article(
            "Parliament opens a new visitor centre and cafeteria",
            "The facility will serve visitors and staff."
        )
        assertEquals("Unmapped", UpscEngine.classify(story).gsPaper)
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun parliamentPassesBillIsRecoveredAsGsTwoFalseNegative() {
        val story = article(
            "Parliament passes a bill to amend electoral rules",
            "The measure changes the Representation of the People Act."
        )
        val relevance = UpscEngine.classify(story)
        assertEquals("GS II", relevance.gsPaper)
        assertEquals("Constitution", relevance.subject)
        assertTrue(relevance.matchedTerms.contains("parliament passes a bill"))
        assertTrue(relevance.matchedTerms.contains("representation of the people act"))
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun standingCommitteeScrutinyRemainsEligible() {
        val story = article(
            "Standing committee reviews a bill on parliamentary privileges",
            "The report examines legislative scrutiny and accountability."
        )
        val relevance = UpscEngine.classify(story)
        assertEquals("GS II", relevance.gsPaper)
        assertEquals("Constitution", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun spacedHeatWaveActionPlanMapsToDisasterManagement() {
        val story = article("NDMA issues heat wave action plan guidelines")
        val relevance = UpscEngine.classify(story)
        assertEquals("GS III", relevance.gsPaper)
        assertEquals("Disaster Management", relevance.subject)
        assertTrue(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun ndmaNameAloneDoesNotQualifyAdministrativeStory() {
        val story = article("NDMA announces routine staffing and office administration changes")
        assertEquals("Unmapped", UpscEngine.classify(story).gsPaper)
        assertFalse(UpscFeedPolicy.shouldInclude(story))
    }

    @Test
    fun syllabusHintShowsSpecificThemeRatherThanOnlyKeywordSignals() {
        val hint = ArticleCardMetaPolicy.syllabusHint(
            gsPaper = "GS I",
            subject = "Geography",
            matchedTerms = listOf("monsoon", "river basin"),
            syllabusLink = "Physical geography; distribution of key natural resources and geophysical phenomena."
        )
        assertTrue(hint!!.contains("GS I"))
        assertTrue(hint.contains("Geography"))
        assertTrue(hint.contains("Syllabus: Physical geography"))
    }
}
