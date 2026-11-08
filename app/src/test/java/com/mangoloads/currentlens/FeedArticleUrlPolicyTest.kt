package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FeedArticleUrlPolicyTest {
    @Test
    fun trimsAndAcceptsAnHttpsArticleOnItsConfiguredPublisher() {
        assertEquals(
            "https://www.thehindu.com/news/national/story",
            FeedArticleUrlPolicy.select(
                "The Hindu",
                "  https://www.thehindu.com/news/national/story  "
            )
        )
    }

    @Test
    fun acceptsConfiguredPublisherArticleDomains() {
        assertEquals(
            "https://indianexpress.com/article/india/story",
            FeedArticleUrlPolicy.select(
                "Indian Express",
                "https://indianexpress.com/article/india/story"
            )
        )
        assertEquals(
            "https://www.bbc.co.uk/news/world/story",
            FeedArticleUrlPolicy.select("BBC", "https://www.bbc.co.uk/news/world/story")
        )
        assertEquals(
            "https://www.dw.com/en/story",
            FeedArticleUrlPolicy.select("DW", "https://www.dw.com/en/story")
        )
        assertEquals(
            "https://www.aljazeera.com/news/story",
            FeedArticleUrlPolicy.select("Al Jazeera", "https://www.aljazeera.com/news/story")
        )
    }

    @Test
    fun rejectsCleartextForeignAndLookalikeDestinations() {
        assertNull(FeedArticleUrlPolicy.select("The Hindu", "http://www.thehindu.com/story"))
        assertNull(FeedArticleUrlPolicy.select("The Hindu", "https://www.bbc.com/news/story"))
        assertNull(FeedArticleUrlPolicy.select("The Hindu", "https://thehindu.com.attacker.test/story"))
        assertNull(FeedArticleUrlPolicy.select("Unknown publisher", "https://news.example.test/story"))
    }

    @Test
    fun rejectsCredentialsAndNonStandardPorts() {
        assertNull(
            FeedArticleUrlPolicy.select(
                "The Hindu",
                "https://user@www.thehindu.com/story"
            )
        )
        assertNull(
            FeedArticleUrlPolicy.select(
                "The Hindu",
                "https://www.thehindu.com:8443/story"
            )
        )
    }

    @Test
    fun rejectsMissingAndOversizedCandidates() {
        assertNull(FeedArticleUrlPolicy.select("The Hindu", null))
        assertNull(FeedArticleUrlPolicy.select("The Hindu", "   "))
        assertNull(
            FeedArticleUrlPolicy.select(
                "The Hindu",
                "https://www.thehindu.com/" + "a".repeat(FeedArticleUrlPolicy.MAX_URL_LENGTH)
            )
        )
    }
}
