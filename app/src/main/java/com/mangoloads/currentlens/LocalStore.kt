package com.mangoloads.currentlens

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object LocalStore {
    private const val PREFS = "currentlens_local"
    private const val BOOKMARKS = "bookmarks"
    private const val READ = "read_history"
    private const val ARTICLES = "article_cache"
    private const val GLASS_ALPHA = "glass_alpha"
    private const val ARTICLE_LIMIT = 300

    fun bookmarks(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getStringSet(BOOKMARKS, emptySet())?.toSet() ?: emptySet()

    fun readHistory(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getStringSet(READ, emptySet())?.toSet() ?: emptySet()

    fun isBookmarked(context: Context, url: String) = bookmarks(context).contains(url)

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
        prefs.edit().putStringSet(BOOKMARKS, next).apply()
        return added
    }

    fun markRead(context: Context, url: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(READ, emptySet())?.toMutableSet() ?: mutableSetOf()
        next.add(url)
        prefs.edit().putStringSet(READ, next).apply()
    }

    fun glassAlpha(context: Context): Float =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getFloat(GLASS_ALPHA, 0.28f)

    fun saveGlassAlpha(context: Context, value: Float) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putFloat(GLASS_ALPHA, value.coerceIn(0.12f, 0.55f)).apply()
    }

    fun cachedArticles(context: Context): List<Article> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(ARTICLES, null)
            ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (i in 0 until array.length()) {
                    val item = array.optJSONObject(i) ?: continue
                    val url = item.optString("url")
                    val title = item.optString("title")
                    if (url.isBlank() || title.isBlank()) continue
                    val time = if (item.isNull("publishedAtMillis")) null else item.optLong("publishedAtMillis")
                    val image = item.optString("imageUrl").takeIf { it.isNotBlank() && it != "null" }
                    add(
                        Article(
                            id = url,
                            title = title,
                            description = item.optString("description"),
                            url = url,
                            source = item.optString("source"),
                            section = item.optString("section"),
                            published = item.optString("published"),
                            imageUrl = image,
                            publishedAtMillis = time,
                            weekendRead = item.optBoolean("weekendRead", false)
                        )
                    )
                }
            }.sortedByDescending { it.publishedAtMillis ?: 0L }
        }.getOrDefault(emptyList())
    }

    fun mergeAndSaveArticles(context: Context, incoming: List<Article>): List<Article> {
        val all = (cachedArticles(context) + incoming)
            .filter { it.url.startsWith("http") }
            .associateBy { it.url }
            .values
            .sortedByDescending { it.publishedAtMillis ?: 0L }
            .take(ARTICLE_LIMIT)
        val array = JSONArray()
        all.forEach { article ->
            array.put(
                JSONObject().apply {
                    put("url", article.url)
                    put("title", article.title)
                    put("description", article.description.take(500))
                    put("source", article.source)
                    put("section", article.section)
                    put("published", article.published)
                    put("publishedAtMillis", article.publishedAtMillis ?: JSONObject.NULL)
                    put("imageUrl", article.imageUrl ?: JSONObject.NULL)
                    put("weekendRead", article.weekendRead)
                }
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(ARTICLES, array.toString()).apply()
        return all
    }
}
