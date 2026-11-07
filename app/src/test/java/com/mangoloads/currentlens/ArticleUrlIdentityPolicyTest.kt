package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleUrlIdentityPolicyTest {
    private fun article(
        id: String,
        url: String,
        published: String,
        feedUrl: String = ""
    ) = Article(
        id = id,
        title = id,
        description = "",
        url = url,
        source = "Test",
        section = "Test",
        published = published,
        feedUrl = feedUrl
    )

    @Test
    fun removesKnownTrackingParametersAndFragmentFromIdentityOnly() {
        val original = "HTTPS://www.example.com:443/world/story?utm_source=rss&id=42&fbclid=click#section"

        assertEquals(
            "https://www.example.com/world/story?id=42",
            ArticleUrlIdentityPolicy.key(original)
        )
    }

    @Test
    fun preservesPublisherQueryParametersAndTheirOrder() {
        assertEquals(
            "https://example.com/story?edition=print&output=1",
            ArticleUrlIdentityPolicy.key(
                "https://example.com/story?edition=print&utm_campaign=weekly&output=1"
            )
        )
    }

    @Test
    fun newestUnbookmarkedTrackingVariantWinsWithoutRewritingItsUrl() {
        val older = article(
            "older",
            "https://example.com/story?utm_source=section",
            "2026-11-05T08:00:00Z",
            "https://example.com/section-feed.xml"
        )
        val newer = article(
            "newer",
            "https://example.com/story?fbclid=campaign",
            "2026-11-06T08:00:00Z",
            "https://example.com/latest-feed.xml"
        )

        val retained = ArticleUrlIdentityPolicy.deduplicate(
            listOf(older, newer),
            bookmarkedUrls = emptySet()
        )

        assertEquals(1, retained.size)
        assertEquals("newer", retained.single().id)
        assertEquals(newer.url, retained.single().url)
    }

    @Test
    fun bookmarkedUrlVariantWinsOverNewerIncomingTrackingVariant() {
        val incoming = article(
            "incoming",
            "https://example.com/story?utm_source=latest",
            "2026-11-06T08:00:00Z"
        )
        val saved = article(
            "saved",
            "https://example.com/story?fbclid=old-share",
            "2026-11-05T08:00:00Z"
        )

        val retained = ArticleUrlIdentityPolicy.deduplicate(
            listOf(incoming, saved),
            bookmarkedUrls = setOf(saved.url)
        )

        assertEquals(1, retained.size)
        assertEquals("saved", retained.single().id)
        assertEquals(saved.url, retained.single().url)
    }

    @Test
    fun equalDateDuplicatesKeepEarlierSectionFeedProvenance() {
        val section = article(
            "section",
            "https://example.com/story?utm_source=section",
            "2026-11-06T08:00:00Z",
            "https://example.com/economy-feed.xml"
        )
        val broad = article(
            "broad",
            "https://example.com/story?utm_medium=all",
            "2026-11-06T08:00:00Z",
            "https://example.com/all-feed.xml"
        )

        val retained = ArticleUrlIdentityPolicy.deduplicate(
            listOf(section, broad),
            bookmarkedUrls = emptySet()
        )

        assertEquals(1, retained.size)
        assertEquals(section.feedUrl, retained.single().feedUrl)
    }

    @Test
    fun previouslyReadVariantWinsOverNewerIncomingTrackingVariant() {
        val previouslyRead = article(
            "read",
            "https://example.com/story?utm_source=section",
            "2026-11-05T08:00:00Z"
        )
        val newer = article(
            "newer",
            "https://example.com/story?fbclid=campaign",
            "2026-11-06T08:00:00Z"
        )

        val retained = ArticleUrlIdentityPolicy.deduplicate(
            listOf(newer, previouslyRead),
            bookmarkedUrls = emptySet(),
            readUrls = setOf(previouslyRead.url)
        )

        assertEquals(1, retained.size)
        assertEquals("read", retained.single().id)
        assertEquals(previouslyRead.url, retained.single().url)
    }

    @Test
    fun meaningfulQueryDifferencesRemainSeparateStories() {
        val first = article("first", "https://example.com/story?id=1", "2026-11-06T08:00:00Z")
        val second = article("second", "https://example.com/story?id=2", "2026-11-06T08:00:00Z")

        val retained = ArticleUrlIdentityPolicy.deduplicate(
            listOf(first, second),
            bookmarkedUrls = emptySet()
        )

        assertEquals(listOf("first", "second"), retained.map { it.id })
    }
}
