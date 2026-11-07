package com.mangoloads.currentlens

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class FeedFetchConcurrencyPolicyTest {
    @Test
    fun capsConcurrentFetchesAndPreservesInputOrder() = runBlocking {
        val active = AtomicInteger()
        val peak = AtomicInteger()
        val input = (0 until 14).toList()

        val result = FeedFetchConcurrencyPolicy.map(input) { value ->
            val concurrent = active.incrementAndGet()
            peak.updateAndGet { previous -> maxOf(previous, concurrent) }
            try {
                delay(5L + (value % 3))
                value * 2
            } finally {
                active.decrementAndGet()
            }
        }

        assertEquals(FeedFetchConcurrencyPolicy.MAX_CONCURRENT_FEEDS, peak.get())
        assertEquals(input.map { it * 2 }, result)
        assertEquals(0, active.get())
    }

    @Test
    fun emptyFeedListReturnsImmediately() = runBlocking {
        val result = FeedFetchConcurrencyPolicy.map(emptyList<Int>()) { it }
        assertTrue(result.isEmpty())
    }
}
