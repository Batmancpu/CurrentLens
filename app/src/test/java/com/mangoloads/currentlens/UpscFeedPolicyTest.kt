package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UpscFeedPolicyTest {
    private fun assertSyllabusMapped(article: Article, expectedPaper: String, expectedSubject: String) {
        val relevance = UpscEngine.classify(article)
        assertTrue("Unexpected GS paper for " + article.title, relevance.gsPaper == expectedPaper)
        assertTrue("Unexpected syllabus theme for " + article.title, relevance.subject == expectedSubject)
        assertTrue("Missing matched syllabus signal for " + article.title, relevance.matchedTerms.isNotEmpty())
        assertTrue("Missing relevance explanation for " + article.title, relevance.syllabusLink.isNotBlank())
        assertTrue(
            "Missing explainable card metadata for " + article.title,
            ArticleCardMetaPolicy.syllabusHint(
                relevance.gsPaper,
                relevance.subject,
                relevance.matchedTerms,
                relevance.syllabusLink
            ) != null
        )
        assertTrue("Editorial gate rejected mapped article " + article.title, UpscFeedPolicy.shouldInclude(article))
    }

    private fun assertRejectedDespiteKeywordMatch(article: Article) {
        assertTrue("Fixture must exercise a classifier match: " + article.title, UpscEngine.classify(article).matchedTerms.isNotEmpty())
        assertFalse(UpscFeedPolicy.shouldInclude(article))
    }

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
        assertRejectedDespiteKeywordMatch(article(
            "Actors prepare for an artificial intelligence role",
            "The feature profiles performers rather than a technology or public-policy development.",
            "India"
        ))
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

    @Test
    fun boxOfficeAndHistoricalFilmHeadlinesCannotBorrowPolicyNouns() {
        assertRejectedDespiteKeywordMatch(article(
            "Box-office collection update: GST on movie tickets raises costs",
            "A box-office report discusses ticket pricing and collections.",
            "Entertainment"
        ))
        assertRejectedDespiteKeywordMatch(article(
            "A new film about the Cinematograph Act enacted in 1952",
            "A promotional film feature describes a historical law.",
            "Entertainment"
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Cricket star's film on national sports policy releases Friday",
            "The story promotes a sports drama and its release date.",
            "Entertainment"
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Actor explains film certification rules in a podcast",
            "A promotional interview accompanies a film release.",
            "Entertainment"
        ))
    }

    @Test
    fun policyExceptionsRequireAnOrderedInstitutionalActionOrCurrentPassiveChange() {
        assertSyllabusMapped(article(
            "Parliament reviews film certification rules under the Cinematograph Act",
            "The report concerns statutory powers, constitutional safeguards and media governance.",
            "Entertainment"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "Government issues new broadcasting regulations for OTT platforms",
            "The ministry notifies a regulatory change for digital broadcasting.",
            "India"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "New film certification rules notified by the Ministry",
            "The notification changes the statutory certification framework.",
            "Entertainment"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "Sports Ministry issues new anti-doping rules",
            "The policy changes sports governance and anti-doping compliance.",
            "Sports"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "CBFC issues new film certification guidelines for actors",
            "The statutory board announces a current certification-rule change.",
            "Entertainment"
        ), "GS II", "Governance")
    }

    @Test
    fun heatWaveActionPlanRemainsARecoverableGsThreeDisasterManagementTheme() {
        assertSyllabusMapped(article(
            "Heat wave action plan adopted by NDMA",
            "The action plan addresses heat-related disaster preparedness and public safety.",
            "India"
        ), "GS III", "Disaster Management")
    }

    @Test
    fun currentGstPolicyActionMapsToGsThreeEconomyButBoxOfficeFramingIsRejected() {
        assertSyllabusMapped(article(
            "Government raises GST on movie tickets",
            "The tax change affects the indirect-tax treatment of cinema tickets.",
            "Economy"
        ), "GS III", "Indian Economy")
    }

    @Test
    fun ambiguousPassivePolicyHeadlineWithoutCurrentCueOrInstitutionIsHeldBack() {
        assertRejectedDespiteKeywordMatch(article(
            "Film certification rules notified",
            "The headline omits the responsible institution and when the change occurred.",
            "Entertainment"
        ))
    }

}
