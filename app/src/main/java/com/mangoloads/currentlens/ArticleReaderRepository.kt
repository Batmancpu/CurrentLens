package com.mangoloads.currentlens

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URI
import java.util.Locale

/**
 * Best-effort extraction from a publicly accessible publisher page.
 *
 * No WebView cookies, subscription tokens, proxying, or paywall circumvention are used.
 * If a page is blocked, paywalled, or too incomplete to form a useful article body,
 * the caller must keep the original publisher page as its in-app fallback.
 */
object ArticleReaderRepository {
    suspend fun extractAccessibleArticle(url: String): String? = withContext(Dispatchers.IO) {
        try {
            val uri = URI(url)
            val scheme = uri.scheme?.lowercase(Locale.ROOT)
            if (scheme !in setOf("http", "https") || uri.host.isNullOrBlank()) {
                null
            } else {
                val response = Jsoup.connect(url)
                    .userAgent("CurrentLens/0.2 (Android; public article reader)")
                    .timeout(12_000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .maxBodySize(2 * 1024 * 1024)
                    .execute()

                if (response.statusCode() !in 200..299) {
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
                                safe.select("img").forEach { image ->
                                    val source = image.absUrl("src")
                                        .ifBlank { image.absUrl("data-src") }
                                        .ifBlank { image.absUrl("data-original") }
                                    if (source.startsWith("https://") || source.startsWith("http://")) {
                                        image.attr("src", source)
                                            .removeAttr("srcset")
                                            .removeAttr("data-src")
                                            .removeAttr("data-original")
                                    } else {
                                        image.remove()
                                    }
                                }
                                safe.html().takeIf { safe.text().length >= 600 && it.isNotBlank() }
                            }
                        }
                    }
                }
            }
        } catch (_: Exception) {
            null
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
