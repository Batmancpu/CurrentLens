package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class ArticleCachePolicyTest {
    private val nowMillis = Instant.parse("2026-11-04T12:00:00Z").toEpochMilli()

    private fun article(id: String, date: String) = Article(
        id = id,
        title = id,
        description = "",
        url = "https://example.test/$id",
        source = "Test",
        section = "Test",
        published = date
    )

    @Test
    fun keepsBookmarkedStoriesWhenNewStoriesFillTheCacheBudget() {
        val pinned = article("pinned", "2026-09-01T10:00:00Z")
        val newest = article("newest", "2026-11-03T10:00:00Z")
        val nextNewest = article("next", "2026-11-02T10:00:00Z")
        val cutoff = LocalDate.of(2026, 10, 4).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

        val retained = ArticleCachePolicy.retain(
            listOf(pinned, newest, nextNewest),
            bookmarkedUrls = setOf(pinned.url),
            cutoffMillis = cutoff,
            maxArticles = 2,
            nowMillis = nowMillis
        )

        assertEquals(listOf("newest", "pinned"), retained.map { it.id })
    }

    @Test
    fun duplicateStoryKeepsTheFirstSectionFeedProvenance() {
        val sectionFeed = article("story", "2026-11-03T10:00:00Z").copy(
            url = "https://example.test/story",
            feedUrl = "https://example.test/economy/feed.xml"
        )
        val broadFeed = sectionFeed.copy(feedUrl = "https://example.test/all/feed.xml")

        val retained = ArticleCachePolicy.retain(
            listOf(sectionFeed, broadFeed),
            bookmarkedUrls = emptySet(),
            cutoffMillis = 0L,
            maxArticles = 10,
            nowMillis = nowMillis
        )

        assertEquals(1, retained.size)
        assertEquals(sectionFeed.feedUrl, retained.single().feedUrl)
    }

    @Test
    fun allBookmarkedStoriesRemainEvenWhenTheyExceedTheSoftLimit() {
        val first = article("first", "2026-09-01T10:00:00Z")
        val second = article("second", "2026-09-02T10:00:00Z")
        val cutoff = LocalDate.of(2026, 10, 4).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

        val retained = ArticleCachePolicy.retain(
            listOf(first, second),
            bookmarkedUrls = setOf(first.url, second.url),
            cutoffMillis = cutoff,
            maxArticles = 1,
            nowMillis = nowMillis
        )

        assertEquals(2, retained.size)
        assertTrue(retained.map { it.url }.containsAll(listOf(first.url, second.url)))
    }

    @Test
    fun undatedAndStaleItemsAreExcludedUnlessExplicitlyBookmarked() {
        val undated = article("undated", "")
        val stale = article("stale", "2026-10-03T23:59:59Z")
        val recent = article("recent", "2026-11-03T10:00:00Z")
        val cutoff = LocalDate.of(2026, 10, 4).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

        val unpinned = ArticleCachePolicy.retain(
            listOf(undated, stale, recent),
            bookmarkedUrls = emptySet(),
            cutoffMillis = cutoff,
            maxArticles = 10,
            nowMillis = nowMillis
        )
        assertEquals(listOf("recent"), unpinned.map { it.id })

        val pinned = ArticleCachePolicy.retain(
            listOf(undated, stale),
            bookmarkedUrls = setOf(undated.url, stale.url),
            cutoffMillis = cutoff,
            maxArticles = 10,
            nowMillis = nowMillis
        )
        assertEquals(setOf("undated", "stale"), pinned.map { it.id }.toSet())
    }

    @Test
    fun materiallyFutureDatedItemsAreExcludedButSmallClockSkewIsTolerated() {
        val slightFuture = article(
            "slight-future",
            Instant.ofEpochMilli(nowMillis + 2L * 60L * 1000L).toString()
        )
        val future = article(
            "future",
            Instant.ofEpochMilli(nowMillis + 10L * 60L * 1000L).toString()
        )

        val retained = ArticleCachePolicy.retain(
            listOf(slightFuture, future),
            bookmarkedUrls = emptySet(),
            cutoffMillis = 0L,
            maxArticles = 10,
            nowMillis = nowMillis
        )

        assertEquals(listOf("slight-future"), retained.map { it.id })
    }
}
