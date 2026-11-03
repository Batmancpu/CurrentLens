package com.mangoloads.currentlens

/** LRU ordering policy for the bounded local dictionary cache. */
internal object DictionaryCachePolicy {
    const val DEFAULT_MAX_ENTRIES = 128

    fun touch(existingKeys: List<String>, key: String, maxEntries: Int): List<String> {
        require(maxEntries > 0) { "maxEntries must be positive" }
        return (existingKeys.filter { it.isNotBlank() && it != key } + key).takeLast(maxEntries)
    }
}

/**
 * Small, process-local LRU for dictionary results.
 *
 * Empty definitions are cached too: they represent a successful lookup with no matching
 * WordNet entry, not a database failure. Access and mutation are synchronized because
 * reader lookups run on Dispatchers.IO and can overlap.
 */
internal class DictionaryDefinitionCache(
    maxEntries: Int = DictionaryCachePolicy.DEFAULT_MAX_ENTRIES
) {
    private val capacity = maxEntries.also { require(it > 0) { "maxEntries must be positive" } }
    private val values = mutableMapOf<String, Definition>()
    private var recency: List<String> = emptyList()

    @Synchronized
    fun get(key: String): Definition? {
        val definition = values[key] ?: return null
        recency = DictionaryCachePolicy.touch(recency, key, capacity)
        return definition
    }

    @Synchronized
    fun put(key: String, definition: Definition) {
        require(key.isNotBlank()) { "key must not be blank" }
        values[key] = definition
        recency = DictionaryCachePolicy.touch(recency, key, capacity)
        val retainedKeys = recency.toHashSet()
        values.keys.retainAll(retainedKeys)
    }

    @Synchronized
    fun size(): Int = values.size
}
