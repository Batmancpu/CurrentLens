package com.mangoloads.currentlens

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId

object LocalStore {
    private const val PREFS = "currentlens_local"
    private const val BOOKMARKS = "bookmarks"
    private const val READ = "read_history"
    private const val ARTICLE_CACHE = "article_cache"
    private const val GLASS_ALPHA = "glass_alpha"
    private const val MAX_CACHED_ARTICLES = 500
    private const val CACHE_DAYS = 30
    private const val DEFAULT_GLASS_ALPHA = 0.28f

    fun bookmarks(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(BOOKMARKS, emptySet())?.toSet() ?: emptySet()

    fun isBookmarked(context: Context, url: String): Boolean = bookmarks(context).contains(url)

    fun toggleBookmark(context: Context, url: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(BOOKMARKS, emptySet())?.toMutableSet() ?: mutableSetOf()
        val added = if (next.contains(url)) {
            next.remove(url)
            false
        } else {
            next.add(url)
            true
        }
        prefs.edit().putStringSet(BOOKMARKS, next.toSet()).apply()
        return added
    }

    fun readHistory(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(READ, emptySet())?.toSet() ?: emptySet()

    fun markRead(context: Context, url: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(READ, emptySet())?.toMutableSet() ?: mutableSetOf()
        next.add(url)
        prefs.edit().putStringSet(READ, next.toSet()).apply()
    }

    fun cachedArticles(context: Context): List<Article> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(ARTICLE_CACHE, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val url = item.optString("url")
                    val title = item.optString("title")
                    if (url.isBlank() || title.isBlank()) continue
                    add(
                        Article(
                            id = item.optString("id").ifBlank { url },
                            title = title,
                            description = item.optString("description"),
                            url = url,
                            source = item.optString("source"),
                            section = item.optString("section"),
                            published = item.optString("published"),
                            imageUrl = item.optString("imageUrl").takeIf { it.isNotBlank() },
                            weekendRead = item.optBoolean("weekendRead", false)
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    fun mergeAndSaveArticles(context: Context, incoming: List<Article>): List<Article> {
        val savedUrls = bookmarks(context)
        val zone = ZoneId.of("Asia/Kolkata")
        val cutoff = LocalDate.now(zone).minusDays(CACHE_DAYS.toLong())
            .atStartOfDay(zone).toInstant().toEpochMilli()
        val merged = ArticleCachePolicy.retain(
            incoming + cachedArticles(context),
            bookmarkedUrls = savedUrls,
            cutoffMillis = cutoff,
            maxArticles = MAX_CACHED_ARTICLES
        )

        val array = JSONArray()
        merged.forEach { article ->
            array.put(
                JSONObject()
                    .put("id", article.id)
                    .put("title", article.title)
                    .put("description", article.description)
                    .put("url", article.url)
                    .put("source", article.source)
                    .put("section", article.section)
                    .put("published", article.published)
                    .put("imageUrl", article.imageUrl ?: JSONObject.NULL)
                    .put("weekendRead", article.weekendRead)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(ARTICLE_CACHE, array.toString()).apply()
        return merged
    }

    fun glassAlpha(context: Context): Float =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getFloat(GLASS_ALPHA, DEFAULT_GLASS_ALPHA).coerceIn(0.12f, 0.55f)

    fun saveGlassAlpha(context: Context, value: Float) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putFloat(GLASS_ALPHA, value.coerceIn(0.12f, 0.55f)).apply()
    }
}
