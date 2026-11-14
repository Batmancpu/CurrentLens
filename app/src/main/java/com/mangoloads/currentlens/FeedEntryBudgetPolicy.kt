package com.mangoloads.currentlens

/**
 * Pure entry-count policy for untrusted RSS/Atom documents.
 *
 * The parser consumes this budget at each item/entry start tag, before it knows
 * whether the record has a usable title or article link. Malformed records therefore
 * consume the same bounded parsing budget as valid records.
 */
internal object FeedEntryBudgetPolicy {
    /**
     * Returns the next count, or null when the configured limit has already been reached.
     */
    fun consume(entriesSeen: Int, maxEntries: Int): Int? {
        require(entriesSeen >= 0) { "entriesSeen must not be negative" }
        require(maxEntries > 0) { "maxEntries must be positive" }
        return if (entriesSeen >= maxEntries) null else entriesSeen + 1
    }
}
