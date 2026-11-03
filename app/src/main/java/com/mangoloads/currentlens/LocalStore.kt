package com.mangoloads.currentlens

import android.content.Context

object LocalStore {
    private const val PREFS = "currentlens_local"
    private const val BOOKMARKS = "bookmarks"
    private const val READ = "read_history"

    fun bookmarks(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getStringSet(BOOKMARKS, emptySet())?.toSet() ?: emptySet()

    fun isBookmarked(context: Context, url: String) = bookmarks(context).contains(url)

    fun toggleBookmark(context: Context, url: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(BOOKMARKS, emptySet())?.toMutableSet() ?: mutableSetOf()
        val added = if (next.contains(url)) { next.remove(url); false } else { next.add(url); true }
        prefs.edit().putStringSet(BOOKMARKS, next).apply()
        return added
    }

    fun markRead(context: Context, url: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(READ, emptySet())?.toMutableSet() ?: mutableSetOf()
        next.add(url)
        prefs.edit().putStringSet(READ, next).apply()
    }
}
