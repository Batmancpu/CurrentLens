package com.mangoloads.currentlens

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * Bounds simultaneous RSS response bodies on memory-constrained Android devices.
 *
 * NewsRepository enforces a 2 MiB ceiling per feed. Limiting the number of active
 * fetch-and-parse operations prevents every configured feed from holding a response
 * buffer and decoded XML string at the same time.
 */
internal object FeedFetchConcurrencyPolicy {
    const val MAX_CONCURRENT_FEEDS = 4

    suspend fun <T, R> map(
        items: List<T>,
        transform: suspend (T) -> R
    ): List<R> = coroutineScope {
        val permits = Semaphore(MAX_CONCURRENT_FEEDS)
        items.map { item ->
            async { permits.withPermit { transform(item) } }
        }.awaitAll()
    }
}
