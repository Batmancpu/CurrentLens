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
            "Parliament amends the Cinematograph Act 1952 in 2026",
            "A current amendment changes the operation of the existing statutory framework.",
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
            "New draft film certification rules notified by the Ministry",
            "The draft notification changes the statutory certification framework.",
            "Entertainment"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "Film certification rules notified by the Ministry today",
            "The named ministry notification is a current statutory certification change.",
            "Entertainment"
        ), "GS II", "Governance")
        assertRejectedDespiteKeywordMatch(article(
            "Film certification rules notified by the Ministry",
            "The headline has no currentness cue and could describe a historical notification.",
            "Entertainment"
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Cinematograph Act enacted by Parliament in 1952",
            "A historical account of the original enactment, not a current regulatory change.",
            "Entertainment"
        ))

        assertRejectedDespiteKeywordMatch(article(
            "Parliament enacted the Cinematograph Act in 1952",
            "An active-voice headline still describes the original historical enactment, not a current change.",
            "Entertainment"
        ))
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
    fun consumerProtectionAndMediaRegulationAreNotSuppressedByEntertainmentWords() {
        assertSyllabusMapped(article(
            "Central Consumer Protection Authority issues new guidelines on celebrity endorsements",
            "The notification implements the Consumer Protection Act, 2019 and sets due-diligence requirements for endorsers.",
            "India"
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "Ministry issues new broadcasting regulations for film trailers",
            "The rules govern public broadcasting and media regulation rather than promoting a film.",
            "India"
        ), "GS II", "Governance")
    }

    @Test
    fun explicitPromotionPhrasesRemainRejectedAfterNarrowingHardNoiseTerms() {
        assertRejectedDespiteKeywordMatch(article(
            "Official trailer released for a film about celebrity endorsements",
            "The story promotes a film and its release rather than reporting a regulator's action.",
            "Entertainment"
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Celebrity gossip column previews a reality show contestant feud",
            "The feature discusses consumer protection rules and celebrity endorsements only in passing.",
            "Entertainment"
        ))
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

        // A genuine institutional tax clarification may mention a noisy sector.
        // Generic box-office reporting remains soft noise and still fails the gate.
        assertSyllabusMapped(article(
            "Finance Ministry clarifies GST on box-office collections",
            "The clarification concerns the indirect-tax treatment of entertainment receipts.",
            "Entertainment"
        ), "GS III", "Indian Economy")
    }

    @Test
    fun specificSyllabusSignalsAreRecognizedByTheirSelectedGsMappings() {
        assertSyllabusMapped(article(
            "Parliament debates a constitutional amendment",
            "The proposed amendment concerns constitutional provisions."
        ), "GS II", "Constitution")
        assertSyllabusMapped(article(
            "National Education Policy review focuses on school education",
            "The review examines education delivery and learning outcomes."
        ), "GS II", "Education & Human Resources")
        assertSyllabusMapped(article(
            "Government expands e-governance and public service delivery",
            "The programme strengthens transparent digital service delivery."
        ), "GS II", "Governance")
        assertSyllabusMapped(article(
            "Gross domestic product growth slows as fiscal deficit widens and monetary policy changes",
            "The official estimate reviews macroeconomic conditions."
        ), "GS III", "Indian Economy")
        assertSyllabusMapped(article(
            "ISRO announces a new space mission and quantum computing programme",
            "The announcement covers scientific and technological applications."
        ), "GS III", "Science & Technology")
        assertSyllabusMapped(article(
            "Border security review strengthens management of the frontier",
            "The review concerns border management and internal security."
        ), "GS III", "Internal Security")
    }

    @Test
    fun institutionNameAloneCannotQualifyRoutineInstitutionNews() {
        assertRejectedDespiteKeywordMatch(article(
            "RBI celebrates foundation day with staff cultural programme",
            "The event reports an internal celebration, not a monetary or financial-policy development."
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Supreme Court registry hosts annual cultural festival",
            "The story concerns an internal staff event, not a constitutional or judicial issue."
        ))
        assertRejectedDespiteKeywordMatch(article(
            "ISRO marks foundation day with employee event",
            "The report covers an internal celebration without a science or space development."
        ))
        assertRejectedDespiteKeywordMatch(article(
            "Election Commission celebrates foundation day",
            "The item is an institutional anniversary without an electoral-process development."
        ))
    }

    @Test
    fun institutionalMentionsRemainEligibleWhenHeadlineNamesTheSyllabusIssue() {
        assertSyllabusMapped(article(
            "RBI holds repo rate as inflation remains a concern",
            "The Reserve Bank reviews monetary policy and liquidity conditions."
        ), "GS III", "Indian Economy")
        assertSyllabusMapped(article(
            "Supreme Court reviews a constitutional amendment affecting federalism",
            "The case concerns constitutional structure and distribution of powers."
        ), "GS II", "Constitution")
        assertSyllabusMapped(article(
            "ISRO announces a new space mission and quantum computing programme",
            "The announcement covers scientific and technological applications."
        ), "GS III", "Science & Technology")
        assertSyllabusMapped(article(
            "Election Commission enforces the Representation of the People Act during polls",
            "The development concerns election law and the constitutional electoral framework."
        ), "GS II", "Constitution")
    }

    @Test
    fun unrelatedSpecificSignalFromAnotherTopicCannotRescueBroadGovernanceMatch() {
        val item = article(
            "Government launches a new welfare scheme for public service reform",
            "A separate unrelated quote briefly mentions climate change.",
            "India"
        )
        val relevance = UpscEngine.classify(item)

        assertTrue("Fixture should select the broad GS II Governance mapping", relevance.gsPaper == "GS II")
        assertTrue("Fixture should select Governance", relevance.subject == "Governance")
        assertTrue("The selected mapping should only contain broad scheme signals",
            relevance.matchedTerms.contains("scheme") && relevance.matchedTerms.contains("welfare") &&
                relevance.matchedTerms.contains("public service"))
        assertFalse("Climate change belongs to another mapping",
            relevance.matchedTerms.contains("climate change"))
        assertFalse("An unrelated environmental phrase must not qualify this welfare headline",
            UpscFeedPolicy.shouldInclude(item))
    }

    @Test
    fun selectedGovernanceMappingStillAcceptsItsOwnSpecificSyllabusSignals() {
        assertSyllabusMapped(article(
            "Government publishes a social audit of a direct benefit transfer scheme",
            "The report examines accountability and public-service delivery."
        ), "GS II", "Governance")
    }

    @Test
    fun unrelatedInstitutionalActionCannotRescueAnEntertainmentSubject() {
        assertRejectedDespiteKeywordMatch(article(
            "A new film about the Cinematograph Act while Ministry issues new agricultural rules",
            "A promotional feature concerns a historical film; the unrelated ministry action is about agriculture.",
            "Entertainment"
        ))
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
