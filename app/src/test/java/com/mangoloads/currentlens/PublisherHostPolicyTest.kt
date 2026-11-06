package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PublisherHostPolicyTest {
    @Test
    fun allowsHttpsPublisherHostsAndTheirSubdomains() {
        assertTrue(PublisherHostPolicy.allows("The Hindu", "https://www.thehindu.com/news/national/story"))
        assertTrue(PublisherHostPolicy.allows("Indian Express", "https://indianexpress.com/article/india/story"))
        assertTrue(PublisherHostPolicy.allows("BBC", "https://www.bbc.co.uk/news/world"))
        assertTrue(PublisherHostPolicy.allows("DW", "https://www.dw.com/en/story"))
        assertTrue(PublisherHostPolicy.allows("Al Jazeera", "https://www.aljazeera.com/news/story"))
    }

    @Test
    fun allowsConfiguredRssFeedHostsIncludingTheOfficialBbcFeedHost() {
        val feeds = listOf(
            "The Hindu" to "https://www.thehindu.com/business/Economy/feeder/default.rss",
            "Indian Express" to "https://indianexpress.com/section/india/feed/",
            "BBC" to "https://feeds.bbci.co.uk/news/world/rss.xml",
            "DW" to "https://rss.dw.com/rdf/rss-en-all",
            "Al Jazeera" to "https://www.aljazeera.com/xml/rss/all.xml"
        )

        feeds.forEach { (source, url) ->
            assertTrue("$source feed host should be allowed", PublisherHostPolicy.allows(source, url))
        }
    }

    @Test
    fun resolvesRelativeRedirectsOnTheOfficialBbcFeedHost() {
        assertEquals(
            "https://feeds.bbci.co.uk/news/world/rss-v2.xml",
            PublisherRedirectPolicy.resolveAllowedTarget(
                source = "BBC",
                currentUrl = "https://feeds.bbci.co.uk/news/world/rss.xml",
                location = "rss-v2.xml",
                redirectsFollowed = 0
            )
        )
    }

    @Test
    fun rejectsOffPublisherRssRedirectsBeforeFollowing() {
        val current = "https://feeds.bbci.co.uk/news/world/rss.xml"
        listOf(
            "https://attacker.example/collect",
            "http://feeds.bbci.co.uk/news/world/rss.xml",
            "https://feeds.bbci.co.uk.attacker.test/rss.xml",
            "https://user@feeds.bbci.co.uk/rss.xml",
            "https://feeds.bbci.co.uk:8443/rss.xml"
        ).forEach { location ->
            assertNull(
                PublisherRedirectPolicy.resolveAllowedTarget(
                    source = "BBC",
                    currentUrl = current,
                    location = location,
                    redirectsFollowed = 0
                )
            )
        }
    }

    @Test
    fun rejectsNonDefaultHttpsPorts() {
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://www.thehindu.com:8443/news/story"))
        assertTrue(PublisherHostPolicy.allows("The Hindu", "https://www.thehindu.com:443/news/story"))
    }

    @Test
    fun resolvesRelativeRedirectsInsideTheSamePublisher() {
        assertEquals(
            "https://www.thehindu.com/news/next",
            PublisherRedirectPolicy.resolveAllowedTarget(
                source = "The Hindu",
                currentUrl = "https://www.thehindu.com/news/story",
                location = "next",
                redirectsFollowed = 0
            )
        )
    }

    @Test
    fun rejectsOffHostDowngradeLookalikeAndCredentialRedirectsBeforeFollowing() {
        val current = "https://www.thehindu.com/news/story"
        listOf(
            "https://attacker.example/collect",
            "http://www.thehindu.com/news/story",
            "https://thehindu.com.attacker.test/story",
            "https://user@www.thehindu.com/story"
        ).forEach { location ->
            assertNull(
                PublisherRedirectPolicy.resolveAllowedTarget(
                    source = "The Hindu",
                    currentUrl = current,
                    location = location,
                    redirectsFollowed = 0
                )
            )
        }
    }

    @Test
    fun stopsFollowingAfterFiveRedirects() {
        assertNull(
            PublisherRedirectPolicy.resolveAllowedTarget(
                source = "The Hindu",
                currentUrl = "https://www.thehindu.com/news/story",
                location = "next",
                redirectsFollowed = PublisherRedirectPolicy.MAX_REDIRECTS
            )
        )
    }

    @Test
    fun rejectsLookalikeHostsWrongPublishersAndUnsafeSchemes() {
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://thehindu.com.attacker.test/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://evilthehindu.com/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://www.bbc.com/news/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "http://www.thehindu.com/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "file:///data/user/0/app/story.html"))
        assertFalse(PublisherHostPolicy.allows("Unknown", "https://news.example.test/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://user@www.thehindu.com/story"))
    }
}
