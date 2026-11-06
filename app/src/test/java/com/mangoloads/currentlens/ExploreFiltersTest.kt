package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExploreFiltersTest {
    private fun article(title: String, description: String, section: String) = Article(
        id = title, title = title, description = description,
        url = "https://example.test/" + title.hashCode(),
        source = "Test", section = section, published = "2026-11-03T10:00:00Z"
    )

    @Test
    fun economyAndScienceMatchEvenWhenPublisherUsesBroadSection() {
        val economy = article("RBI holds repo rate", "Inflation and monetary policy remain in focus.", "India")
        val science = article("ISRO tests a new launch system", "The space programme advances.", "India")
        assertTrue(ExploreFilters.matchesCategory(economy, "Economy"))
        assertFalse(ExploreFilters.matchesCategory(economy, "Science"))
        assertTrue(ExploreFilters.matchesCategory(science, "Science"))
    }

    @Test
    fun topicAndSyllabusQueryTermsAreCombined() {
        val climate = article("India expands renewable energy capacity", "Climate policy and biodiversity are discussed.", "India")
        assertTrue(ExploreFilters.matches(climate, "India", "GS3, climate"))
        assertFalse(ExploreFilters.matches(climate, "World", "GS3, climate"))
        assertTrue(ExploreFilters.matches(climate, "All", "renewable, biodiversity"))
    }

    @Test
    fun sharedEditorialIndexKeepsExploreFreeOfEntertainmentNoise() {
        val relevant = article(
            "RBI holds repo rate amid inflation concerns",
            "The monetary policy committee explains the inflation outlook.",
            "India"
        )
        val entertainment = article(
            "Celebrity film trailer celebrates Himalayan glaciers",
            "A movie uses glacier and river-basin imagery in a fictional landscape.",
            "Entertainment"
        )

        val index = EditorialFeedIndexPolicy.build(
            listOf(relevant, entertainment),
            java.time.LocalDate.of(2026, 11, 3)
        )

        assertTrue(index.exploreArticles.contains(relevant))
        assertFalse(index.exploreArticles.contains(entertainment))
    }

    @Test
    fun environmentAndOpinionMatchPublisherSectionOrMetadata() {
        val environment = article("Forest conservation plan announced", "Wildlife habitat restoration.", "India")
        val editorial = article("A view on public policy", "The government needs reform.", "Editorial")
        assertTrue(ExploreFilters.matchesCategory(environment, "Environment"))
        assertTrue(ExploreFilters.matchesCategory(editorial, "Opinion"))
    }
}
