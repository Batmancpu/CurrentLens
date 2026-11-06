package com.mangoloads.currentlens

/**
 * Ordered retention for newly written read markers.
 *
 * The legacy SharedPreferences StringSet is intentionally kept as a frozen,
 * unordered snapshot; only new markers enter this bounded insertion-ordered window.
 */
internal object ReadHistoryPolicy {
    const val MAX_RECENT_ENTRIES = 5_000
    const val MAX_URL_LENGTH = 2_048

    fun retain(
        existing: List<String>,
        maxEntries: Int = MAX_RECENT_ENTRIES
    ): List<String> {
        if (maxEntries <= 0) return emptyList()
        val unique = LinkedHashSet<String>()
        existing.forEach { url ->
            if (url.isNotBlank() && url.length <= MAX_URL_LENGTH) unique.add(url)
        }
        return unique.toList().takeLast(maxEntries)
    }

    fun visibleHistory(
        legacy: Set<String>,
        recent: List<String>,
        maxEntries: Int = MAX_RECENT_ENTRIES
    ): Set<String> = legacy + retain(recent, maxEntries)

    fun add(
        existing: List<String>,
        url: String,
        maxEntries: Int = MAX_RECENT_ENTRIES
    ): List<String> {
        val retained = retain(existing, maxEntries)
        if (url.isBlank() || url.length > MAX_URL_LENGTH || maxEntries <= 0 || url in retained) {
            return retained
        }
        return (retained + url).takeLast(maxEntries)
    }
}
