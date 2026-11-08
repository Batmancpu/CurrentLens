package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class NewsRepositoryTest {
    private val source = FeedSource(
        name = "The Hindu",
        section = "India",
        url = "https://www.thehindu.com/feed.xml"
    )

    // Android's Html.fromHtml is a framework stub in local JVM tests. The parser
    // receives an identity sanitizer here so these tests exercise XML/entry limits,
    // while production keeps the default Android HTML sanitizer.
    private val plainTextSanitizer: (String) -> String = { it }

    @Test
    fun parserCapsValidEntriesBeforeTheCrossFeedMerge() {
        val xml = buildString {
            append("<rss><channel>")
            repeat(NewsRepository.MAX_ENTRIES_PER_FEED + 25) { index ->
                append("<item>")
                append("<title>Story $index</title>")
                append("<link>https://www.thehindu.com/story/$index</link>")
                append("</item>")
            }
            append("</channel></rss>")
        }

        val articles = NewsRepository.parse(source, xml, plainTextSanitizer)

        assertEquals(NewsRepository.MAX_ENTRIES_PER_FEED, articles.size)
        assertEquals(
            "Story ${NewsRepository.MAX_ENTRIES_PER_FEED - 1}",
            articles.last().title
        )
    }

    @Test
    fun malformedEntriesAlsoConsumeThePerFeedParsingBudget() {
        val xml = buildString {
            append("<rss><channel>")
            repeat(NewsRepository.MAX_ENTRIES_PER_FEED) { index ->
                append("<item><title>Unusable $index</title></item>")
            }
            append("<item><title>Later valid story</title>")
            append("<link>https://www.thehindu.com/story/later</link></item>")
            append("</channel></rss>")
        }

        assertEquals(
            emptyList<Article>(),
            NewsRepository.parse(source, xml, plainTextSanitizer)
        )
    }
}
