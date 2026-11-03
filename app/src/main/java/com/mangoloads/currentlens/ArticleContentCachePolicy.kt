package com.mangoloads.currentlens

internal object ArticleContentCachePolicy {
    fun retain(
        entries: List<ArticleContentCacheEntry>,
        nowMillis: Long,
        maxAgeMillis: Long,
        maxEntries: Int,
        maxBytes: Long,
        maxEntryBytes: Long
    ): List<String> {
        require(maxAgeMillis >= 0 && maxEntries > 0 && maxBytes > 0 && maxEntryBytes > 0)
        val eligible = entries
            .filter { entry ->
                val age = nowMillis - entry.lastAccessMillis
                entry.key.isNotBlank() &&
                    entry.sizeBytes in 1..maxEntryBytes &&
                    age in 0..maxAgeMillis
            }
            .sortedWith(compareByDescending<ArticleContentCacheEntry> { it.lastAccessMillis }.thenBy { it.key })

        val retained = mutableListOf<String>()
        var bytesUsed = 0L
        for (entry in eligible) {
            if (retained.size >= maxEntries) break
            if (entry.sizeBytes > maxBytes - bytesUsed) continue
            retained += entry.key
            bytesUsed += entry.sizeBytes
        }
        return retained
    }
}
