package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PublisherRedirectPolicyTest {
    @Test
    fun recognisesOnlyStandardHttpRedirectStatuses() {
        listOf(301, 302, 303, 307, 308).forEach {
            assertTrue(PublisherRedirectPolicy.isRedirectStatus(it))
        }
        listOf(200, 300, 304, 400, 500).forEach {
            assertFalse(PublisherRedirectPolicy.isRedirectStatus(it))
        }
    }

    @Test
    fun resolvesRelativeAndAbsoluteRedirectsWithinTheSamePublisher() {
        assertEquals(
            "https://www.thehindu.com/news/new-story",
            PublisherRedirectPolicy.resolveAllowedTarget(
                "The Hindu",
                "https://www.thehindu.com/feeds/current.rss",
                "../news/new-story",
                0
            )
        )
        assertEquals(
            "https://feeds.thehindu.com/rss/current",
            PublisherRedirectPolicy.resolveAllowedTarget(
                "The Hindu",
                "https://www.thehindu.com/feeds/current.rss",
                "https://feeds.thehindu.com/rss/current",
                0
            )
        )
    }

    @Test
    fun rejectsCrossPublisherLookalikeAndCleartextRedirects() {
        val current = "https://www.thehindu.com/feeds/current.rss"
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "https://evil.example.test/feed", 0))
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "https://thehindu.com.attacker.test/feed", 0))
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "http://www.thehindu.com/feed", 0))
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", "https://evil.example.test/feed", "/next", 0))
    }

    @Test
    fun rejectsMissingOversizedAndExhaustedRedirects() {
        val current = "https://www.thehindu.com/feeds/current.rss"
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "", 0))
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "x".repeat(4_097), 0))
        assertNull(PublisherRedirectPolicy.resolveAllowedTarget("The Hindu", current, "/next", -1))
        assertNull(
            PublisherRedirectPolicy.resolveAllowedTarget(
                "The Hindu",
                current,
                "/next",
                PublisherRedirectPolicy.MAX_REDIRECTS
            )
        )
    }
}
