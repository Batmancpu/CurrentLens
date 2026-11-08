package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ArticleRelevanceIndexPolicyTest {
    private fun article(
        id: String,
        title: String,
        description: String
    ) = Article(
        id = id,
        title = title,
        description = description,
        url = "https://example.test/$id",
        source = "Test",
        section = "India",
        published = "2026-11-03T10:00:00Z"
    )

    private fun relevance(gsPaper: String, subject: String) = Relevance(
        gsPaper = gsPaper,
        subject = subject,
        syllabusLink = "Test syllabus",
        matchedTerms = emptyList(),
        signalLabel = "Cached",
        topicKey = "cached"
    )

    @Test
    fun cacheKeyTracksOnlyIdentityAndClassifierInputs() {
        val original = article("one", "Monsoon patterns", "Rainfall and river basins.")
        val displayMetadataChange = original.copy(
            url = "https://example.test/updated",
            source = "Another publisher",
            section = "World",
            published = "2026-11-04T10:00:00Z",
            imageUrl = "https://example.test/image.jpg",
            weekendRead = true,
            feedUrl = "https://example.test/feed.xml"
        )
        assertEquals(
            ArticleRelevanceIndexPolicy.keys(listOf(original)),
            ArticleRelevanceIndexPolicy.keys(listOf(displayMetadataChange))
        )
        assertNotEquals(
            ArticleRelevanceIndexPolicy.keys(listOf(original)),
            ArticleRelevanceIndexPolicy.keys(listOf(original.copy(title = "Cyclone update")))
        )
        assertNotEquals(
            ArticleRelevanceIndexPolicy.keys(listOf(original)),
            ArticleRelevanceIndexPolicy.keys(listOf(original.copy(description = "New excerpt")))
        )
        assertNotEquals(
            ArticleRelevanceIndexPolicy.keys(listOf(original)),
            ArticleRelevanceIndexPolicy.keys(listOf(original.copy(id = "new-id")))
        )
    }

    @Test
    fun indexClassifiesEachArticleOnceAndPreservesFeedOrder() {
        val articles = listOf(
            article("first", "Monsoon update", "Rainfall"),
            article("second", "RBI policy", "Inflation")
        )
        val calls = mutableListOf<String>()
        val index = ArticleRelevanceIndexPolicy.build(articles) { item ->
            calls += item.id
            relevance("GS III", item.id)
        }

        assertEquals(listOf("first", "second"), calls)
        assertEquals(listOf("first", "second"), index.keys.toList())
        assertEquals("first", index["first"]?.subject)
        assertEquals("second", index["second"]?.subject)
    }

    @Test
    fun searchSyllabusTermsUseTheSuppliedCachedRelevance() {
        val article = article("monsoon", "Monsoon patterns", "Rainfall and river basins.")
        // The cached result intentionally differs from the classifier's natural result.
        val cached = relevance("GS II", "Constitution")

        assertTrue(ExploreFilters.matches(article, "All", "GS II", cached))
        assertFalse(ExploreFilters.matches(article, "All", "GS I", cached))
        assertFalse(ExploreFilters.matches(article, "All", "Geography", cached))
    }
}
