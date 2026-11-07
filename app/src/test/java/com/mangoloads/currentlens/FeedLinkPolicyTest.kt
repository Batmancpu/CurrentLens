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

    @Test
    fun relativeAlternateResolvesAgainstFinalFeedDocumentUrl() {
        val relative = FeedLinkCandidate(
            href = "stories/42",
            rel = "alternate",
            type = "text/html"
        )
        assertEquals(
            "https://example.test/feeds/stories/42",
            FeedLinkPolicy.select(listOf(relative), "https://example.test/feeds/world.xml")
        )
    }

    @Test
    fun rootRelativeAlternateResolvesAgainstFeedOrigin() {
        val relative = FeedLinkCandidate(
            href = "/world/story/42",
            rel = "alternate",
            type = "text/html"
        )
        assertEquals(
            "https://example.test/world/story/42",
            FeedLinkPolicy.select(listOf(relative), "https://example.test/feeds/world.xml")
        )
    }

    @Test
    fun linkLevelXmlBaseOverridesDocumentBase() {
        val relative = FeedLinkCandidate(
            href = "story/42",
            rel = "alternate",
            type = "text/html",
            baseUrl = "https://example.test/world/"
        )
        assertEquals(
            "https://example.test/world/story/42",
            FeedLinkPolicy.select(listOf(relative), "https://example.test/feeds/world.xml")
        )
    }

    @Test
    fun xmlBaseCanItselfBeRelativeToItsParentBase() {
        assertEquals(
            "https://example.test/world/",
            FeedLinkPolicy.resolveHttpReference(
                "../world/",
                "https://example.test/feeds/archive/feed.xml"
            )
        )
    }

    @Test
    fun relativeAlternateWithoutBaseAndFragmentOnlyReferenceAreRejected() {
        assertNull(
            FeedLinkPolicy.select(
                listOf(FeedLinkCandidate("story/42", rel = "alternate", type = "text/html"))
            )
        )
        assertNull(
            FeedLinkPolicy.select(
                listOf(FeedLinkCandidate("#story", rel = "alternate", type = "text/html")),
                "https://example.test/feed.xml"
            )
        )
    }

    @Test
    fun nonHttpXmlBaseCannotMakeUnsafeLinkSelectable() {
        val unsafeBase = FeedLinkCandidate(
            href = "story/42",
            rel = "alternate",
            type = "text/html",
            baseUrl = "javascript:alert(1)"
        )
        assertNull(FeedLinkPolicy.select(listOf(unsafeBase)))
    }
}
