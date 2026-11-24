package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ReviewPolicyTest {
    private fun story(id: String) = Article(id, "RBI repo rate", "", "https://example.test/$id",
        "Test", "Economy", "2026-11-24T08:00:00Z")
    private fun attempt(a: Article, time: Long) = QuizAttempt("attempt-$time", a.url, a.title,
        "Economy", "GS III", time, 5, 5, 3)

    @Test fun savedOnlyAndLeastRecentlyPractisedFirst() {
        val a = story("a"); val b = story("b"); val c = story("c"); val d = story("d")
        val queue = ReviewPolicy.queue(listOf(a, b, c, d, a), setOf(a.url, b.url, c.url),
            listOf(attempt(a, 10), attempt(a, 30), attempt(b, 20)))
        assertEquals(listOf(c.url, b.url, a.url), queue.map { it.article.url })
        assertEquals(listOf(null, 20L, 30L), queue.map { it.lastPracticeMillis })
    }

    @Test fun boundsPrivateNotes() {
        assertEquals("hello", ReviewPolicy.normalizeNote("  hello  "))
        assertEquals(2000, ReviewPolicy.normalizeNote("x".repeat(3000)).length)
    }
}
