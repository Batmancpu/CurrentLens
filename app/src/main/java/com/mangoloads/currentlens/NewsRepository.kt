package com.mangoloads.currentlens

import android.text.Html
import java.io.IOException
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

private val FEED_REDIRECT_STATUS_CODES = setOf(301, 302, 303, 307, 308)

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
        val fetched = FeedFetchConcurrencyPolicy.map(feeds) { source -> fetchOne(source) }
            .flatten()
        ArticleUrlIdentityPolicy.deduplicate(fetched, bookmarkedUrls = emptySet()).take(250)
    }

    private fun fetchOne(source: FeedSource): List<Article> {
        return try {
            val connection = openAllowedFeedConnection(source) ?: return emptyList()
            try {
                if (connection.contentLengthLong > NetworkLimits.MAX_RSS_BYTES) {
                    emptyList()
                } else {
                    connection.inputStream.use { input ->
                        parse(source, NetworkLimits.readUtf8Limited(input, NetworkLimits.MAX_RSS_BYTES))
                    }
                }
            } finally {
                connection.disconnect()
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    /**
     * RSS feeds are network inputs too: disable automatic redirects and validate each
     * destination against the feed's publisher before issuing the next request.
     */
    private fun openAllowedFeedConnection(source: FeedSource): HttpURLConnection? {
        var currentUrl = source.url
        var redirectsFollowed = 0

        while (true) {
            if (!PublisherHostPolicy.allows(source.name, currentUrl)) return null

            val connection = (URL(currentUrl).openConnection() as HttpURLConnection).apply {
                connectTimeout = 8000
                readTimeout = 10000
                requestMethod = "GET"
                instanceFollowRedirects = false
                setRequestProperty("User-Agent", "CurrentLens/0.2 RSS reader")
                setRequestProperty(
                    "Accept",
                    "application/rss+xml, application/atom+xml, application/xml, text/xml"
                )
            }

            val status = try {
                connection.responseCode
            } catch (error: Exception) {
                connection.disconnect()
                throw error
            }

            if (status in FEED_REDIRECT_STATUS_CODES) {
                val nextUrl = connection.getHeaderField("Location")?.let { location ->
                    PublisherRedirectPolicy.resolveAllowedTarget(
                        source = source.name,
                        currentUrl = currentUrl,
                        location = location,
                        redirectsFollowed = redirectsFollowed
                    )
                }
                connection.disconnect()
                if (nextUrl == null) return null
                currentUrl = nextUrl
                redirectsFollowed += 1
                continue
            }

            if (status !in 200..299 ||
                !PublisherHostPolicy.allows(source.name, connection.url.toExternalForm())
            ) {
                connection.disconnect()
                return null
            }

            return connection
        }
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
        val links = mutableListOf<FeedLinkCandidate>()
        var rssPubDate = ""
        var atomPublished = ""
        var atomUpdated = ""
        var genericDate = ""
        var image: String? = null
        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    val tag = parser.name?.substringAfter(':')?.lowercase(Locale.ROOT).orEmpty()
                    if (tag == "item" || tag == "entry") {
                        inItem = true
                        title = ""
                        description = ""
                        links.clear()
                        rssPubDate = ""
                        atomPublished = ""
                        atomUpdated = ""
                        genericDate = ""
                        image = null
                    } else if (inItem) {
                        when (tag) {
                            "title" -> title = clean(runCatching { parser.nextText() }.getOrDefault(""))
                            "description", "summary", "encoded" -> {
                                val text = clean(runCatching { parser.nextText() }.getOrDefault(""))
                                if (text.length > description.length) description = text
                            }
                            "link" -> {
                                val href = parser.getAttributeValue(null, "href")
                                if (!href.isNullOrBlank()) {
                                    links.add(
                                        FeedLinkCandidate(
                                            href = href.trim(),
                                            rel = parser.getAttributeValue(null, "rel").orEmpty(),
                                            type = parser.getAttributeValue(null, "type").orEmpty()
                                        )
                                    )
                                } else {
                                    val textLink = runCatching { parser.nextText().trim() }.getOrDefault("")
                                    if (textLink.isNotBlank()) links.add(FeedLinkCandidate(href = textLink))
                                }
                            }
                            "pubdate" -> rssPubDate = runCatching { parser.nextText().trim() }.getOrDefault("")
                            "published" -> atomPublished = runCatching { parser.nextText().trim() }.getOrDefault("")
                            "updated" -> atomUpdated = runCatching { parser.nextText().trim() }.getOrDefault("")
                            "date" -> genericDate = runCatching { parser.nextText().trim() }.getOrDefault("")
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
                        val articleUrl = FeedLinkPolicy.select(links)
                        if (title.isNotBlank() && articleUrl != null) {
                            result.add(
                                Article(
                                    id = articleUrl,
                                    title = title,
                                    description = description.take(420),
                                    url = articleUrl,
                                    source = source.name,
                                    section = source.section,
                                    published = FeedPublicationDatePolicy.select(
                                        rssPubDate = rssPubDate,
                                        atomPublished = atomPublished,
                                        atomUpdated = atomUpdated,
                                        genericDate = genericDate
                                    ),
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
