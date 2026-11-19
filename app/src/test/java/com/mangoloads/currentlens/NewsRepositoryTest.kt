package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NewsRepositoryTest {
    @Test
    fun invalidFutureAndUndatedEntriesCannotCrowdOutValidNews() {
        val now = java.time.Instant.parse("2026-11-19T10:00:00Z").toEpochMilli()
        fun story(id: String, whenPublished: String) = Article(
            id = id, title = id, description = "", url = "https://example.test/$id",
            source = "Test", section = "India", published = whenPublished
        )
        val future = List(300) { index ->
            story("future-$index", "2026-11-20T10:00:00Z")
        }
        val current = story("current", "2026-11-19T09:00:00Z")
        val smallClockSkew = story("skew", "2026-11-19T10:02:00Z")
        val undated = story("undated", "")

        val result = NewsRepository.selectFreshArticles(
            future + undated + current + smallClockSkew, now
        )

        assertEquals(listOf("skew", "current"), result.map { it.id })
    }

    @Test
    fun perFeedBudgetAcceptsExactlyTheConfiguredNumberOfEntries() {
        val limit = NewsRepository.MAX_ENTRIES_PER_FEED
        var seen = 0

        repeat(limit) {
            seen = requireNotNull(FeedEntryBudgetPolicy.consume(seen, limit))
        }

        assertEquals(limit, seen)
        assertNull(FeedEntryBudgetPolicy.consume(seen, limit))
    }

    @Test
    fun malformedEntriesConsumeBudgetBeforeALaterValidEntryCanBeParsed() {
        val limit = NewsRepository.MAX_ENTRIES_PER_FEED
        // The parser calls consume at each item/entry start tag, before checking
        // title/link validity. Model a feed with 100 malformed records then a valid one.
        val entryHasUsableArticleLink = List(limit) { false } + true
        var seen = 0
        val acceptedEntryIndexes = mutableListOf<Int>()

        for ((index, isUsable) in entryHasUsableArticleLink.withIndex()) {
            val nextCount = FeedEntryBudgetPolicy.consume(seen, limit) ?: break
            seen = nextCount
            if (isUsable) acceptedEntryIndexes += index
        }

        assertEquals(limit, seen)
        assertTrue(acceptedEntryIndexes.isEmpty())
    }

    @Test
    fun atomParserKeepsSafeAlternateRelativeLinkAndInlineText() {
        val source = FeedSource("BBC", "World", "https://feeds.bbci.co.uk/rss")
        val xml = """
            <feed xmlns="http://www.w3.org/2005/Atom" xml:base="https://www.bbc.com/news/">
                <entry xml:base="world/">
                    <title>One story</title>
                    <link rel="alternate" type="text/html" href="https://example.com/story"/>
                    <link rel="alternate" type="text/html" href="story"/>
                    <content type="xhtml"><div xmlns="http://www.w3.org/1999/xhtml">
                        <p>One</p><p>two</p>
                    </div></content>
                </entry>
            </feed>
        """.trimIndent()
        val article = NewsRepository.parse(source, xml, source.url) {
            it.replace(Regex("\\s+"), " ").trim()
        }.single()
        assertEquals("https://www.bbc.com/news/world/story", article.url)
        assertEquals("One two", article.description)
        assertEquals(source.url, article.feedUrl)
    }

    @Test
    fun validEmptyFeedIsDistinctFromNonFeedResponse() {
        val source = FeedSource("BBC", "World", "https://feeds.bbci.co.uk/rss")
        assertTrue(NewsRepository.parse(source, "<rss><channel/></rss>", source.url) { it }.isEmpty())
        org.junit.Assert.assertThrows(IllegalArgumentException::class.java) {
            NewsRepository.parse(source, "<html><body>Unavailable</body></html>", source.url) { it }
        }
    }
}
