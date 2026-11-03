package com.mangoloads.currentlens

import android.text.Html
import kotlinx.coroutines.Dispatchers
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
    val imageUrl: String? = null
)
data class FeedSource(val name: String, val section: String, val url: String)

object NewsRepository {
    private val feeds = listOf(
        FeedSource("The Hindu", "India", "https://www.thehindu.com/feeder/default.rss"),
        FeedSource("The Hindu", "World", "https://www.thehindu.com/news/international/feeder/default.rss"),
        FeedSource("Indian Express", "India", "https://indianexpress.com/section/india/feed/"),
        FeedSource("Indian Express", "Explained", "https://indianexpress.com/section/explained/feed/"),
        FeedSource("Indian Express", "UPSC", "https://indianexpress.com/section/upsc-current-affairs/feed/"),
        FeedSource("BBC", "World", "https://feeds.bbci.co.uk/news/world/rss.xml"),
        FeedSource("DW", "World", "https://rss.dw.com/rdf/rss-en-all"),
        FeedSource("Al Jazeera", "World", "https://www.aljazeera.com/xml/rss/all.xml")
    )

    suspend fun fetchAll(): List<Article> = withContext(Dispatchers.IO) {
        feeds.flatMap { fetchOne(it) }
            .distinctBy { it.url.ifBlank { it.title } }
            .sortedByDescending { it.published }
            .take(120)
    }

    private fun fetchOne(source: FeedSource): List<Article> = try {
        val connection = (URL(source.url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8000
            readTimeout = 10000
            requestMethod = "GET"
            setRequestProperty("User-Agent", "CurrentLens/0.1 RSS reader")
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
            .replace(Regex("\\\\s+"), " ").trim()

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
                    if (tag == "item" || tag == "entry") {
                        inItem = true
                        title = ""; description = ""; link = ""; published = ""; image = null
                    } else if (inItem) {
                        when (tag) {
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
                    if (tag == "item" || tag == "entry") {
                        if (title.isNotBlank() && link.startsWith("http")) {
                            result.add(Article(link, title, description.take(420), link, source.name, source.section, published, image))
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
