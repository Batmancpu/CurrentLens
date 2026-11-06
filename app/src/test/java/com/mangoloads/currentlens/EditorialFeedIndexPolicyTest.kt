package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class EditorialFeedIndexPolicyTest {
    @Test
    fun indexesEligibleStoriesOnceForTodayExploreAndSearch() {
        val today = LocalDate.of(2026, 11, 6)
        val relevant = Article(
            id = "rbi",
            title = "RBI holds repo rate as inflation remains a concern",
            description = "The monetary policy committee reviews liquidity conditions.",
            url = "https://example.test/rbi",
            source = "Test",
            section = "Economy",
            published = "2026-11-06T04:00:00Z"
        )
        val noise = Article(
            id = "film",
            title = "Celebrity film box office collection update",
            description = "Opening weekend numbers dominate entertainment coverage.",
            url = "https://example.test/film",
            source = "Test",
            section = "Entertainment",
            published = "2026-11-06T05:00:00Z"
        )

        val index = EditorialFeedIndexPolicy.build(listOf(relevant, noise), today)

        assertEquals(listOf(relevant), index.eligibleArticles)
        assertEquals(listOf(relevant), index.todayArticles)
        assertEquals(listOf(relevant), index.exploreArticles)
        assertFalse(index.eligibleArticles.contains(noise))
    }

    @Test
    fun olderEligibleArticlesRemainInExploreButNotToday() {
        val today = LocalDate.of(2026, 11, 6)
        val article = Article(
            id = "climate",
            title = "Parliament reviews climate change safeguards",
            description = "The debate covers biodiversity and environmental impact assessment.",
            url = "https://example.test/climate",
            source = "Test",
            section = "Environment",
            published = "2026-11-04T04:00:00Z"
        )

        val index = EditorialFeedIndexPolicy.build(listOf(article), today)

        assertTrue(index.todayArticles.isEmpty())
        assertEquals(listOf(article), index.exploreArticles)
    }
}
