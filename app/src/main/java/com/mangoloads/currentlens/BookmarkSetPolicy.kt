package com.mangoloads.currentlens

/** Pure saved-URL mutation; no retention cap is applied to user bookmarks. */
internal data class BookmarkMutation(
    val savedUrls: Set<String>,
    val added: Boolean,
    val changed: Boolean
)

internal object BookmarkSetPolicy {
    fun toggle(existing: Set<String>, url: String): BookmarkMutation {
        if (url.isBlank()) return BookmarkMutation(existing, added = false, changed = false)
        val next = existing.toMutableSet()
        val added = if (next.remove(url)) {
            false
        } else {
            next.add(url)
            true
        }
        return BookmarkMutation(next.toSet(), added = added, changed = true)
    }
}
