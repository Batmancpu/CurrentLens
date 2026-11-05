package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UpscFeedPolicyTest {
    private fun article(title: String, description: String, section: String = "India") = Article(
        id = title,
        title = title,
        description = description,
        url = "https://example.test/" + title.hashCode(),
        source = "Test",
        section = section,
        published = "2026-11-05T10:00:00Z"
    )

    @Test
    fun suppressesCelebrityFilmBoxOfficeAndRealityShowNoise() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Ranbir Kapoor prepares for Ramayana",
            "The actor's upcoming movie generates entertainment headlines.",
            "Entertainment"
        )))
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Clayface box office collection update",
            "The movie's opening weekend and global collections dominate coverage.",
            "Entertainment"
        )))
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Salman Khan gets angry at a contestant",
            "A reality show episode becomes a celebrity talking point.",
            "Entertainment"
        )))
    }

    @Test
    fun includesSpecificSyllabusLinkedPublicAffairs() {
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "RBI holds repo rate as inflation remains a concern",
            "The Reserve Bank reviews monetary policy and liquidity conditions."
        )))
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "Parliament debates climate change and biodiversity safeguards",
            "New environmental impact assessment provisions are discussed."
        )))
    }

    @Test
    fun oneBroadKeywordIsNotEnoughForDefaultFeedEligibility() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Technology trends reshape everyday life",
            "A general feature describes consumer technology and gadgets."
        )))
    }

    @Test
    fun publicPolicyCoverageCanPassDespiteEntertainmentVocabulary() {
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "Parliament reviews film certification rules under the Cinematograph Act",
            "The debate concerns regulatory powers, constitutional safeguards and media governance."
        )))
    }
}
