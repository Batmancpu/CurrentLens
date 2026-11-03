package com.mangoloads.currentlens

/** LRU ordering policy for the bounded local dictionary cache. */
internal object DictionaryCachePolicy {
    fun touch(existingKeys: List<String>, key: String, maxEntries: Int): List<String> {
        require(maxEntries > 0) { "maxEntries must be positive" }
        return (existingKeys.filter { it.isNotBlank() && it != key } + key).takeLast(maxEntries)
    }
}
