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

private const val MEDIA_RSS_NAMESPACE = "http://search.yahoo.com/mrss/"
private const val XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace"
private val FEED_REDIRECT_STATUS_CODES = setOf(301, 302, 303, 307, 308)

object NewsRepository {
    internal const val MAX_ENTRIES_PER_FEED = 100

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
        val fetched = FeedFetchConcurrencyPolicy.map(feeds, ::fetchOne)
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
                        parse(
                            source,
                            NetworkLimits.readUtf8Limited(input, NetworkLimits.MAX_RSS_BYTES),
                            connection.url.toExternalForm()
                        )
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

    internal fun parse(
        source: FeedSource,
        xml: String,
        documentUrl: String,
        sanitizeText: (String) -> String = ::clean
    ): List<Article> {
        val result = mutableListOf<Article>()
        val factory = XmlPullParserFactory.newInstance().apply { setNamespaceAware(true) }
        val parser = factory.newPullParser()
        runCatching { parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false) }
        parser.setInput(xml.reader())
        var event = parser.eventType
        val baseByDepth = mutableMapOf<Int, String>()
        var itemDepth: Int? = null
        var entriesSeen = 0
        var title = ""
        var description = ""
        val links = mutableListOf<FeedLinkCandidate>()
        var rssPubDate = ""
        var atomPublished = ""
        var atomUpdated = ""
        var genericDate = ""
        val images = mutableListOf<FeedImageCandidate>()
        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    val depth = parser.depth
                    val parentBase = baseByDepth[depth - 1] ?: documentUrl
                    val declaredBase = FeedFieldPolicy.url(
                        parser.getAttributeValue(XML_NAMESPACE, "base").orEmpty()
                    )
                    val effectiveBase = declaredBase?.let {
                        FeedLinkPolicy.resolveHttpReference(it, parentBase)
                    } ?: parentBase
                    baseByDepth[depth] = effectiveBase

                    val tag = parser.name?.lowercase(Locale.ROOT).orEmpty()
                    val isMediaImage = itemDepth != null && depth > itemDepth &&
                        parser.namespace == MEDIA_RSS_NAMESPACE && (tag == "thumbnail" || tag == "content")
                    if (itemDepth == null && (tag == "item" || tag == "entry")) {
                        entriesSeen = FeedEntryBudgetPolicy.consume(entriesSeen, MAX_ENTRIES_PER_FEED)
                            ?: break
                        itemDepth = depth
                        title = ""
                        description = ""
                        links.clear()
                        images.clear()
                        rssPubDate = ""
                        atomPublished = ""
                        atomUpdated = ""
                        genericDate = ""
                    } else if (itemDepth == depth - 1 || isMediaImage) {
                        when (tag) {
                            "title" -> title = sanitizeText(
                                FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS)
                            )
                            "description", "summary", "encoded" -> {
                                val text = sanitizeText(
                                    FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS)
                                )
                                if (text.length > description.length) description = text
                            }
                            "link" -> {
                                val rel = parser.getAttributeValue(null, "rel").orEmpty()
                                val type = parser.getAttributeValue(null, "type").orEmpty()
                                val href = parser.getAttributeValue(null, "href")
                                    ?: FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_URL_CHARS + 1)
                                FeedFieldPolicy.url(href)?.let {
                                    links.add(FeedLinkCandidate(it, rel, type, effectiveBase))
                                }
                            }
                            "pubdate" -> rssPubDate = readDate(parser)
                            "published" -> atomPublished = readDate(parser)
                            "updated" -> atomUpdated = readDate(parser)
                            "date" -> genericDate = readDate(parser)
                            "enclosure", "thumbnail", "content" -> {
                                val rawUrl = parser.getAttributeValue(null, "url")
                                val type = parser.getAttributeValue(null, "type")
                                if (tag == "content" && FeedFieldPolicy.isInlineAtomTextContent(
                                        parser.namespace, type, parser.getAttributeValue(null, "src"), rawUrl
                                    )
                                ) {
                                    val text = sanitizeText(
                                        FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS)
                                    )
                                    if (text.length > description.length) description = text
                                } else {
                                    val element = when (tag) {
                                        "enclosure" -> FeedImageElement.ENCLOSURE
                                        "thumbnail" -> FeedImageElement.THUMBNAIL
                                        else -> FeedImageElement.CONTENT
                                    }
                                    FeedFieldPolicy.url(rawUrl.orEmpty())?.let { url ->
                                        images.add(
                                            FeedImageCandidate(
                                                url, element, type.orEmpty(),
                                                parser.getAttributeValue(null, "medium").orEmpty()
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (parser.eventType == XmlPullParser.END_TAG) baseByDepth.remove(depth)
                }
                XmlPullParser.END_TAG -> {
                    if (parser.depth == itemDepth) {
                        val safeLinks = links.mapNotNull { candidate ->
                            val resolved = FeedLinkPolicy.resolveHttpReference(candidate.href, candidate.baseUrl)
                            val url = FeedArticleUrlPolicy.select(source.name, resolved) ?: return@mapNotNull null
                            candidate.copy(href = url)
                        }
                        val articleUrl = FeedLinkPolicy.select(safeLinks)
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
                                    imageUrl = FeedImagePolicy.select(source.name, images),
                                    weekendRead = source.weekendRead,
                                    feedUrl = source.url
                                )
                            )
                        }
                        itemDepth = null
                    }
                    baseByDepth.remove(parser.depth)
                }
            }
            event = parser.next()
        }
        return result
    }

    private fun readDate(parser: XmlPullParser): String = FeedFieldPolicy.date(
        FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_DATE_CHARS + 1)
    ).orEmpty()
}
