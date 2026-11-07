package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FeedLinkPolicyTest {
    private val self = FeedLinkCandidate(
        href = "https://example.test/feed.xml",
        rel = "self",
        type = "application/atom+xml"
    )
    private val article = FeedLinkCandidate(
        href = "https://example.test/story",
        rel = "alternate",
        type = "text/html"
    )

    @Test
    fun htmlAlternateWinsOverSelfRegardlessOfDocumentOrder() {
        assertEquals("https://example.test/story", FeedLinkPolicy.select(listOf(self, article)))
        assertEquals("https://example.test/story", FeedLinkPolicy.select(listOf(article, self)))
    }

    @Test
    fun rssTextLinkWithoutAtomAttributesRemainsSupported() {
        assertEquals(
            "https://example.test/rss-story",
            FeedLinkPolicy.select(listOf(FeedLinkCandidate("https://example.test/rss-story")))
        )
    }

    @Test
    fun alternateHtmlIsPreferredOverAnUnspecifiedAlternate() {
        val unspecified = FeedLinkCandidate("https://example.test/generic")
        assertEquals(
            "https://example.test/story",
            FeedLinkPolicy.select(listOf(unspecified, article))
        )
    }

    @Test
    fun selfEnclosureAndUnsafeSchemesAreNotArticleDestinations() {
        val enclosure = FeedLinkCandidate(
            href = "https://example.test/audio.mp3",
            rel = "enclosure",
            type = "audio/mpeg"
        )
        val unsafe = FeedLinkCandidate(
            href = "javascript:alert(1)",
            rel = "alternate",
            type = "text/html"
        )
        assertNull(FeedLinkPolicy.select(listOf(self, enclosure, unsafe)))
    }

    @Test
    fun absoluteAlternateRelationUriIsRecognised() {
        val alternate = article.copy(
            rel = "http://www.iana.org/assignments/relation/alternate"
        )
        assertEquals("https://example.test/story", FeedLinkPolicy.select(listOf(alternate)))
    }
}
