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
