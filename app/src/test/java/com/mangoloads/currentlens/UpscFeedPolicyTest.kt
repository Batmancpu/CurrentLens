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
    fun broadTermsAndPublisherSectionsCannotQualifyAStoryAlone() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Technology trends reshape everyday life",
            "A general feature describes consumer technology and gadgets.",
            "UPSC"
        )))
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Women and health in everyday life",
            "A general lifestyle feature discusses wellness and routines.",
            "Explained"
        )))
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Culture and policy in a popular series",
            "A general entertainment feature discusses public taste.",
            "UPSC"
        )))
    }

    @Test
    fun suppressesRoutineSportsScoresAndGenericViralNoiseDespiteIncidentalSyllabusWords() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "India vs Australia final score: 3-1",
            "A match report mentions inflation only in a sponsor's unrelated statement.",
            "Sports"
        )))
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Viral dance challenge becomes an internet sensation",
            "The clip uses climate change and biodiversity imagery as a backdrop.",
            "Trending"
        )))
    }

    @Test
    fun preservesSpecificSportsGovernancePolicyDevelopments() {
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "Sports ministry revises anti-doping rules",
            "The institutional change concerns sports governance and regulatory oversight.",
            "Sports"
        )))
    }

    @Test
    fun tokenBoundariesPreventIplAndActorSubstringFalsePositives() {
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "Multiple irrigation projects receive clearance",
            "The report explains a key factor in groundwater management."
        )))
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "RBI inflation outlook remains uncertain",
            "The policy committee discusses monetary conditions and a key factor in price stability."
        )))
    }

    @Test
    fun pluralEntertainmentTermsRemainSuppressedWithoutSectionHelp() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Actors prepare for an artificial intelligence role",
            "The feature profiles performers rather than a technology or public-policy development.",
            "India"
        )))
    }

    @Test
    fun unrelatedWordArbitraryCannotBecomeAnRbiSyllabusSignal() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Arbitrary changes to a local event schedule",
            "Residents prepare for a neighbourhood gathering.",
            "Local"
        )))
    }

    @Test
    fun entertainmentPolicyExceptionMustBeEstablishedInTheHeadline() {
        assertFalse(UpscFeedPolicy.shouldInclude(article(
            "Salman Khan film release draws attention",
            "A passing paragraph mentions film certification rules and a wider policy debate.",
            "Entertainment"
        )))
        assertTrue(UpscFeedPolicy.shouldInclude(article(
            "Parliament reviews film certification rules under the Cinematograph Act",
            "The report concerns statutory powers, constitutional safeguards and media governance.",
            "Entertainment"
        )))
    }
}
