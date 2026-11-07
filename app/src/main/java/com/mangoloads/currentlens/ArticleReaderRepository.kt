package com.mangoloads.currentlens

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Safelist
import java.net.URI
import java.util.Locale

/**
 * Best-effort extraction from a publicly accessible publisher page.
 *
 * No WebView cookies, subscription tokens, proxying, or paywall circumvention are used.
 * If a page is blocked, paywalled, or too incomplete to form a useful article body,
 * the caller must keep the original publisher page as its in-app fallback.
 */
/**
 * Keeps readable editorial markup while stripping scripts, inline event handlers,
 * unsafe URL schemes, embedded documents and all attributes outside the allow-list.
 */
internal fun sanitizeArticleHtml(bodyHtml: String, baseUri: String): String? = runCatching {
    val policy = Safelist.none()
        .addTags(
            "p", "div", "span", "br", "hr",
            "h1", "h2", "h3", "h4", "h5", "h6",
            "blockquote", "ul", "ol", "li",
            "strong", "b", "em", "i", "u", "s", "sup", "sub",
            "figure", "figcaption", "img", "a",
            "table", "thead", "tbody", "tfoot", "tr", "th", "td", "caption",
            "dl", "dt", "dd", "pre", "code"
        )
        .addAttributes("a", "href", "title")
        .addAttributes("img", "src", "alt", "title")
        .addAttributes("td", "colspan", "rowspan")
        .addAttributes("th", "colspan", "rowspan", "scope")
        .addProtocols("a", "href", "https", "http")
        .addProtocols("img", "src", "https", "http")
    val cleaned = Jsoup.clean(bodyHtml, baseUri, policy)
    if (cleaned.isBlank()) return@runCatching null

    // Publisher markup remains allow-list sanitised. Add this wrapper only after cleaning,
    // so the horizontal-scrolling hook is app-owned and cannot be supplied by page HTML.
    val fragment = Jsoup.parseBodyFragment(cleaned, baseUri)
    fragment.select("table").forEach { table ->
        if (table.parent()?.hasClass("cl-table-scroll") != true) {
            table.wrap("<div class=\"cl-table-scroll\"></div>")
        }
    }
    val readableHtml = fragment.body().html()
    readableHtml.takeIf {
        it.isNotBlank() && Jsoup.parseBodyFragment(it).text().length >= 600
    }
}.getOrNull()

/**
 * Remove publisher-page images that would cause off-publisher or cleartext requests.
 * The same filter is applied to legacy cached HTML before it is rendered.
 */
internal fun filterPublisherImageUrls(bodyHtml: String, baseUri: String, source: String): String =
    runCatching {
        val fragment = Jsoup.parseBodyFragment(bodyHtml, baseUri)
        fragment.select("img").forEach { image ->
            val imageUrl = image.absUrl("src")
                .ifBlank { image.absUrl("data-src") }
                .ifBlank { image.absUrl("data-original") }
            if (PublisherAssetHostPolicy.allows(source, imageUrl)) {
                image.attr("src", imageUrl)
                    .removeAttr("srcset")
                    .removeAttr("data-src")
                    .removeAttr("data-original")
            } else {
                image.remove()
            }
        }
        fragment.body().html()
    }.getOrDefault("")


private val PUBLISHER_REDIRECT_STATUS_CODES = setOf(301, 302, 303, 307, 308)

object ArticleReaderRepository {
    suspend fun extractAccessibleArticle(url: String, source: String): String? = withContext(Dispatchers.IO) {
        try {
            if (!PublisherHostPolicy.allows(source, url)) {
                null
            } else {
                val response = fetchAllowedPublisherResponse(url, source)

                if (response == null ||
                    response.statusCode() !in 200..299 ||
                    !PublisherHostPolicy.allows(source, response.url().toExternalForm())
                ) {
                    null
                } else {
                    val document = response.parse()
                    if (looksPaywalled(document)) {
                        null
                    } else {
                        val body = findArticleBody(document)
                        if (body == null) {
                            null
                        } else {
                            val paragraphs = body.select("p").filter { it.text().trim().length >= 40 }
                            val articleText = paragraphs.joinToString(" ") { it.text() }
                            if (paragraphs.size < 3 || articleText.length < 600) {
                                null
                            } else {
                                val safe = body.clone()
                                safe.select(
                                    "script,style,iframe,form,button,nav,footer,header,aside," +
                                        ".advertisement,.advert,.ad,.social-share,.related-articles,.newsletter-signup"
                                ).remove()
                                safe.select("[style*=display:none], [hidden]").remove()
                                safe.select("a[href]").forEach { link ->
                                    val target = link.absUrl("href")
                                    if (target.startsWith("https://") || target.startsWith("http://")) {
                                        link.attr("href", target)
                                    } else {
                                        link.unwrap()
                                    }
                                }
                                val publisherSafeBody = filterPublisherImageUrls(
                                    bodyHtml = safe.html(),
                                    baseUri = safe.baseUri(),
                                    source = source
                                )
                                sanitizeArticleHtml(publisherSafeBody, safe.baseUri())
                            }
                        }
                    }
                }
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun fetchAllowedPublisherResponse(
        initialUrl: String,
        source: String
    ): Connection.Response? {
        var currentUrl = initialUrl
        var redirectsFollowed = 0

        while (true) {
            // Validate before every request. Automatic redirects are deliberately disabled.
            if (!PublisherHostPolicy.allows(source, currentUrl)) return null

            val response = Jsoup.connect(currentUrl)
                .userAgent("CurrentLens/0.2 (Android; public article reader)")
                .timeout(12_000)
                .followRedirects(false)
                .ignoreHttpErrors(true)
                .maxBodySize(2 * 1024 * 1024)
                .execute()

            if (response.statusCode() in PUBLISHER_REDIRECT_STATUS_CODES) {
                val location = response.header("Location")?.takeIf { it.isNotBlank() } ?: return null
                val nextUrl = PublisherRedirectPolicy.resolveAllowedTarget(
                    source = source,
                    currentUrl = currentUrl,
                    location = location,
                    redirectsFollowed = redirectsFollowed
                ) ?: return null

                currentUrl = nextUrl
                redirectsFollowed += 1
                continue
            }

            if (response.statusCode() !in 200..299 ||
                !PublisherHostPolicy.allows(source, response.url().toExternalForm())
            ) {
                return null
            }
            return response
        }
    }

    private fun findArticleBody(document: Document) =
        document.select(
            "article,[itemprop=articleBody],.articlebodycontent,.story_details,.article-body," +
                ".story-content,.story__content,.article-content,.content__article-body"
        ).maxByOrNull { element -> element.select("p").sumOf { it.text().length } }
            ?: document.select("main").maxByOrNull { element ->
                element.select("p").sumOf { it.text().length }
            }

    private fun looksPaywalled(document: Document): Boolean {
        val paywallElement = document.select(
            "[class*=paywall],[id*=paywall],[data-paywall],[class*=subscriber-only],[id*=subscriber-only]"
        ).any { it.text().isNotBlank() }
        if (paywallElement) return true

        val pageText = document.body()?.text()?.lowercase(Locale.ROOT).orEmpty()
        val paywallPhrases = listOf(
            "subscribe to continue reading",
            "subscribe to read",
            "unlock this article",
            "already a subscriber",
            "subscriber only",
            "you have reached your free article limit",
            "continue reading with a subscription"
        )
        return paywallPhrases.any { pageText.contains(it) }
    }
}
