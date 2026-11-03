package com.mangoloads.currentlens

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Date and tab filters shared by the UI and JVM unit tests. */
object ArticleFilters {
    private val indiaZone = ZoneId.of("Asia/Kolkata")

    fun publishedDate(article: Article, zone: ZoneId = indiaZone): LocalDate? =
        article.publishedAtMillis?.let {
            runCatching { Instant.ofEpochMilli(it).atZone(zone).toLocalDate() }.getOrNull()
        }

    fun today(articles: List<Article>, date: LocalDate, zone: ZoneId = indiaZone): List<Article> =
        articles.filter { publishedDate(it, zone) == date }
            .sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }

    /** Explore is intentionally broader than Today: it includes the full retained local cache. */
    fun explore(articles: List<Article>): List<Article> =
        articles.sortedByDescending { it.publishedAtMillis ?: Long.MIN_VALUE }

}
