package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleProvenancePolicyTest {
    @Test
    fun configuredPublisherRssIsExplicitlyLabelledSecondary() {
        assertEquals(
            "Publisher RSS · secondary source",
            ArticleProvenancePolicy.label(
                "The Hindu",
                "https://www.thehindu.com/business/Economy/feeder/default.rss"
            )
        )
    }

    @Test
    fun legacyCachedArticleDoesNotClaimFeedProvenance() {
        assertEquals(
            "Feed provenance unavailable",
            ArticleProvenancePolicy.label("The Hindu", "")
        )
    }

    @Test
    fun unrecognisedSourceIsNotPresentedAsPrimaryEvidence() {
        assertEquals(
            "Source type unverified",
            ArticleProvenancePolicy.label("PIB", "https://pib.gov.in/example.rss")
        )
    }
}
