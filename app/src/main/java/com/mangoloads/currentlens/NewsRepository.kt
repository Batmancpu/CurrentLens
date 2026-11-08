package com.mangoloads.currentlens

import android.text.Html
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val source: String,
    val section: String,
    val published: String,
    val imageUrl: String? = null,
    val weekendRead: Boolean = false,
    /** Configured RSS endpoint that supplied this story; blank only for legacy cached entries. */
    val feedUrl: String = ""
) {
    val publishedAtMillis: Long?
        get() = FeedDateParser.parse(published)
}

data class FeedSource(
    val name: String,
    val section: String,
    val url: String,
    val weekendRead: Boolean = false
)

object NewsRepository {
    private val feeds = listOf(
        // Section feeds precede broad feeds so duplicate URLs keep useful labels.
        FeedSource("The Hindu", "Economy", "https://www.thehindu.com/business/Economy/feeder/default.rss"),
        FeedSource("The Hindu", "Science", "https://www.thehindu.com/sci-tech/science/feeder/default.rss"),
        FeedSource("The Hindu", "Environment", "https://www.thehindu.com/sci-tech/energy-and-environment/feeder/default.rss"),
        FeedSource("The Hindu", "Editorial", "https://www.thehindu.com/opinion/editorial/feeder/default.rss"),
        FeedSource("The Hindu", "India", "https://www.thehindu.com/news/national/feeder/default.rss"),
        FeedSource("The Hindu", "World", "https://www.thehindu.com/news/international/feeder/default.rss"),
        FeedSource("The Hindu", "Latest", "https://www.thehindu.com/feeder/default.rss"),
        FeedSource("Indian Express", "India", "https://indianexpress.com/section/india/feed/"),
        FeedSource("Indian Express", "Explained", "https://indianexpress.com/section/explained/feed/"),
        FeedSource("Indian Express", "UPSC", "https://indianexpress.com/section/upsc-current-affairs/feed/"),
        FeedSource("Indian Express", "Express Sunday Eye", "https://indianexpress.com/section/express-sunday-eye/feed/", weekendRead = true),
        FeedSource("BBC", "World", "https://feeds.bbci.co.uk/news/world/rss.xml"),
        FeedSource("DW", "World", "https://rss.dw.com/rdf/rss-en-all"),
        FeedSource("Al Jazeera", "World", "https://www.aljazeera.com/xml/rss/all.xml")
    )

    suspend fun fetchAll(): List<Article> = withContext(Dispatchers.IO) {
        feeds.map { source -> async { fetchOne(source) } }
            .awaitAll()
            .flatten()
            .distinctBy { it.url.ifBlank { it.title } }
            .sortedByDescending { it.publishedAtMillis ?: 0L }
            .take(250)
    }

    private fun fetchOne(source: FeedSource): List<Article> = try {
        val connection = (URL(source.url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8000
            readTimeout = 10000
            requestMethod = "GET"
            setRequestProperty("User-Agent", "CurrentLens/0.2 RSS reader")
            setRequestProperty("Accept", "application/rss+xml, application/atom+xml, application/xml, text/xml")
        }
        try {
            val tooLarge = connection.contentLengthLong > NetworkLimits.MAX_RSS_BYTES
            val status = if (tooLarge) -1 else connection.responseCode
            if (tooLarge || status !in 200..299) emptyList()
            else connection.inputStream.use { input ->
                parse(source, NetworkLimits.readUtf8Limited(input, NetworkLimits.MAX_RSS_BYTES))
            }
        } finally {
            connection.disconnect()
        }
    } catch (_: Exception) {
        emptyList()
    }

    private fun clean(value: String): String =
        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()
            .replace(Regex("\\s+"), " ").trim()

    private fun parse(source: FeedSource, xml: String): List<Article> {
        val result = mutableListOf<Article>()
        val factory = XmlPullParserFactory.newInstance().apply { setNamespaceAware(true) }
        val parser = factory.newPullParser()
        runCatching { parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false) }
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
                    val tag = parser.name?.substringAfter(':')?.lowercase(Locale.ROOT).orEmpty()
                    if (tag == "item" || tag == "entry") {
                        inItem = true
                        title = ""
                        description = ""
                        link = ""
                        published = ""
                        image = null
                    } else if (inItem) {
                        when (tag) {
                            "title" -> title = clean(FeedFieldPolicy.titleSource(runCatching { parser.nextText() }.getOrDefault("")))
                            "description", "summary", "encoded" -> {
                                val text = clean(FeedFieldPolicy.descriptionSource(runCatching { parser.nextText() }.getOrDefault("")))
                                if (text.length > description.length) description = text
                            }
                            "link" -> {
                                val href = parser.getAttributeValue(null, "href")
                                if (!href.isNullOrBlank()) link = FeedFieldPolicy.url(href).orEmpty()
                                else if (link.isBlank()) {
                                    link = FeedFieldPolicy.url(
                                        runCatching { parser.nextText() }.getOrDefault("")
                                    ).orEmpty()
                                }
                            }
                            "pubdate", "published", "updated", "date" ->
                                published = FeedFieldPolicy.date(
                                    runCatching { parser.nextText() }.getOrDefault("")
                                ).orEmpty()
                            "enclosure" -> image = FeedFieldPolicy.url(parser.getAttributeValue(null, "url").orEmpty())
                            "thumbnail", "content" -> {
                                val candidate = FeedFieldPolicy.url(parser.getAttributeValue(null, "url").orEmpty())
                                if (candidate?.startsWith("http") == true) image = candidate
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    val tag = parser.name?.lowercase(Locale.ROOT).orEmpty()
                    if (tag == "item" || tag == "entry") {
                        if (title.isNotBlank() && link.startsWith("http")) {
                            result.add(
                                Article(
                                    id = link,
                                    title = title,
                                    description = description.take(420),
                                    url = link,
                                    source = source.name,
                                    section = source.section,
                                    published = published,
                                    imageUrl = image,
                                    weekendRead = source.weekendRead,
                                    feedUrl = source.url
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
