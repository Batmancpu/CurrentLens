package com.mangoloads.currentlens

import android.text.Html
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val source: String,
    val section: String,
    val published: String,
    val imageUrl: String? = null,
    val publishedAtMillis: Long? = null,
    val weekendRead: Boolean = false
)

data class FeedSource(
    val name: String,
    val section: String,
    val url: String,
    val weekendRead: Boolean = false
)

object NewsRepository {
    private val feeds = listOf(
        FeedSource("The Hindu", "India", "https://www.thehindu.com/news/national/feeder/default.rss"),
        FeedSource("The Hindu", "World", "https://www.thehindu.com/news/international/feeder/default.rss"),
        FeedSource("The Hindu", "News", "https://www.thehindu.com/feeder/default.rss"),
        FeedSource("The Hindu", "Opinion", "https://www.thehindu.com/opinion/feeder/default.rss", true),
        FeedSource("The Hindu", "Editorials", "https://www.thehindu.com/opinion/editorial/feeder/default.rss", true),
        FeedSource("The Hindu", "Economy", "https://www.thehindu.com/business/feeder/default.rss"),
        FeedSource("The Hindu", "Science", "https://www.thehindu.com/sci-tech/feeder/default.rss"),
        FeedSource("The Hindu", "Books", "https://www.thehindu.com/books/feeder/default.rss", true),
        FeedSource("The Hindu", "Culture & Life", "https://www.thehindu.com/life-and-style/feeder/default.rss", true),
        FeedSource("Indian Express", "India", "https://indianexpress.com/section/india/feed/"),
        FeedSource("Indian Express", "World", "https://indianexpress.com/section/world/feed/"),
        FeedSource("Indian Express", "Explained", "https://indianexpress.com/section/explained/feed/"),
        FeedSource("Indian Express", "Economy", "https://indianexpress.com/section/business/economy/feed/"),
        FeedSource("Indian Express", "Science", "https://indianexpress.com/section/technology/science/feed/"),
        FeedSource("Indian Express", "UPSC", "https://indianexpress.com/section/upsc-current-affairs/feed/"),
        FeedSource("Indian Express", "Sunday Eye", "https://indianexpress.com/section/express-sunday-eye/feed/", true),
        FeedSource("Indian Express", "Long Reads", "https://indianexpress.com/section/long-reads/feed/", true),
        FeedSource("BBC", "World", "https://feeds.bbci.co.uk/news/world/rss.xml"),
        FeedSource("DW", "World", "https://rss.dw.com/rdf/rss-en-all")
    )

    suspend fun fetchAll(): List<Article> = withContext(Dispatchers.IO) {
        val batches = coroutineScope {
            feeds.map { feed -> async(Dispatchers.IO) { fetchOne(feed) } }.awaitAll()
        }
        batches.flatten()
            .distinctBy { it.url.ifBlank { it.title } }
            .sortedByDescending { it.publishedAtMillis ?: 0L }
            .take(400)
    }

    private fun fetchOne(source: FeedSource): List<Article> = try {
        val connection = (URL(source.url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 7000
            readTimeout = 9000
            requestMethod = "GET"
            instanceFollowRedirects = true
            setRequestProperty("User-Agent", "CurrentLens/0.2 Android RSS reader")
            setRequestProperty("Accept", "application/rss+xml, application/atom+xml, application/xml, text/xml")
        }
        try {
            connection.inputStream.bufferedReader().use { parse(source, it.readText()) }
        } finally {
            connection.disconnect()
        }
    } catch (_: Exception) {
        emptyList()
    }

    private fun clean(value: String): String =
        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()
            .replace(Regex("\\s+"), " ").trim()

    private fun parsePublishedAt(raw: String): Long? {
        val value = raw.trim()
        if (value.isBlank()) return null
        try { return Instant.parse(value).toEpochMilli() } catch (_: Exception) { }
        try { return OffsetDateTime.parse(value).toInstant().toEpochMilli() } catch (_: Exception) { }
        try { return ZonedDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli() } catch (_: Exception) { }
        val patterns = listOf(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            "EEE, d MMM yyyy HH:mm:ss Z",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
        )
        for (pattern in patterns) {
            try {
                val formatter = SimpleDateFormat(pattern, Locale.ENGLISH).apply {
                    isLenient = true
                    timeZone = TimeZone.getTimeZone("Asia/Kolkata")
                }
                return formatter.parse(value)?.time
            } catch (_: Exception) { }
        }
        return null
    }

    private fun parse(source: FeedSource, xml: String): List<Article> {
        val result = mutableListOf<Article>()
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(xml.reader())
        var event = parser.eventType
        var inItem = false
        var title = ""
        var description = ""
        var link = ""
        var published = ""
        var image: String? = null

        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    val tag = parser.name?.lowercase(Locale.ROOT).orEmpty()
                    val localTag = tag.substringAfter(':')
                    if (localTag == "item" || localTag == "entry") {
                        inItem = true
                        title = ""
                        description = ""
                        link = ""
                        published = ""
                        image = null
                    } else if (inItem) {
                        when (localTag) {
                            "title" -> title = clean(runCatching { parser.nextText() }.getOrDefault(""))
                            "description", "summary", "encoded" -> {
                                val text = clean(runCatching { parser.nextText() }.getOrDefault(""))
                                if (text.length > description.length) description = text
                            }
                            "link" -> {
                                val href = parser.getAttributeValue(null, "href")
                                if (!href.isNullOrBlank()) link = href
                                else if (link.isBlank()) link = runCatching { parser.nextText().trim() }.getOrDefault("")
                            }
                            "pubdate", "published", "updated", "date" ->
                                published = runCatching { parser.nextText().trim() }.getOrDefault("")
                            "enclosure" -> image = parser.getAttributeValue(null, "url")
                            "thumbnail", "content" -> {
                                val candidate = parser.getAttributeValue(null, "url")
                                if (candidate?.startsWith("http") == true) image = candidate
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    val tag = parser.name?.lowercase(Locale.ROOT).orEmpty()
                    val localTag = tag.substringAfter(':')
                    if (localTag == "item" || localTag == "entry") {
                        if (title.isNotBlank() && link.startsWith("http")) {
                            result.add(
                                Article(
                                    id = link,
                                    title = title,
                                    description = description.take(500),
                                    url = link,
                                    source = source.name,
                                    section = source.section,
                                    published = published,
                                    imageUrl = image,
                                    publishedAtMillis = parsePublishedAt(published),
                                    weekendRead = source.weekendRead
                                )
                            )
                        }
                        inItem = false
                    }
                }
            }
            event = parser.next()
        }
        return result
    }
}
