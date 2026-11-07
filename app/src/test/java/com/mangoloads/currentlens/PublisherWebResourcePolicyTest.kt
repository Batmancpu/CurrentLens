package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PublisherWebResourcePolicyTest {
    @Test
    fun allowsPublisherPagesAndObservedFirstPartyAssetHosts() {
        assertTrue(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://www.thehindu.com/news/national/story"
            )
        )
        assertTrue(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://th-i.thgim.com/public/story/image.jpg"
            )
        )
        assertTrue(
            PublisherWebResourcePolicy.allows(
                "Indian Express",
                "https://images.indianexpress.com/2026/11/story.jpg"
            )
        )
        assertTrue(
            PublisherWebResourcePolicy.allows(
                "BBC",
                "https://ichef.bbci.co.uk/ace/standard/240/story.jpg"
            )
        )
    }

    @Test
    fun blocksThirdPartyAnalyticsAdvertisingAndCrossPublisherRequests() {
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://www.google-analytics.com/g/collect"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "BBC",
                "https://www.googletagmanager.com/gtag/js?id=example"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "Indian Express",
                "https://ads.example.test/pixel.gif"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://images.indianexpress.com/story.jpg"
            )
        )
    }

    @Test
    fun rejectsLookalikesUnknownPublishersAndUnsafeSchemes() {
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://thehindu.com.attacker.test/pixel"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "http://www.thehindu.com/image.jpg"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "Unknown",
                "https://news.example.test/story"
            )
        )
    }

    @Test
    fun rejectsCredentialsAndNonDefaultPortsOnAdditionalAssetHosts() {
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://user@th-i.thgim.com/image.jpg"
            )
        )
        assertFalse(
            PublisherWebResourcePolicy.allows(
                "The Hindu",
                "https://th-i.thgim.com:8443/image.jpg"
            )
        )
    }
}
