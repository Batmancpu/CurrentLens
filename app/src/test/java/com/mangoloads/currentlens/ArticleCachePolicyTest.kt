package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset

class ArticleCachePolicyTest {
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
            maxArticles = 2
        )

        assertEquals(listOf("newest", "pinned"), retained.map { it.id })
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
            maxArticles = 1
        )

        assertEquals(2, retained.size)
        assertTrue(retained.map { it.url }.containsAll(listOf(first.url, second.url)))
    }
}
