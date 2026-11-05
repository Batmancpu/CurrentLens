package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderPositionPolicyTest {
    @Test
    fun storesOnlyMeaningfulIncompleteProgress() {
        assertFalse(ReaderPositionPolicy.shouldPersist(0f))
        assertFalse(ReaderPositionPolicy.shouldPersist(0.01f))
        assertTrue(ReaderPositionPolicy.shouldPersist(0.25f))
        assertFalse(ReaderPositionPolicy.shouldPersist(0.97f))
        assertFalse(ReaderPositionPolicy.shouldPersist(Float.NaN))
    }

    @Test
    fun mapsScrollToNormalizedProgressAndBackAcrossDifferentViewportSizes() {
        val progress = ReaderPositionPolicy.progressFromScroll(
            scrollY = 600,
            contentHeightPx = 2_000,
            viewportHeightPx = 800
        )
        assertEquals(0.5f, progress!!, 0.001f)
        assertEquals(
            900,
            ReaderPositionPolicy.scrollYForProgress(
                progress = progress,
                contentHeightPx = 2_600,
                viewportHeightPx = 800
            )
        )
    }

    @Test
    fun rejectsNonScrollableOrInvalidGeometry() {
        assertNull(ReaderPositionPolicy.progressFromScroll(0, 500, 800))
        assertNull(ReaderPositionPolicy.progressFromScroll(-1, 2_000, 800))
        assertNull(ReaderPositionPolicy.progressFromScroll(20, 0, 800))
        assertNull(ReaderPositionPolicy.scrollYForProgress(0.5f, 800, 800))
        assertNull(ReaderPositionPolicy.scrollYForProgress(0.98f, 2_000, 800))
    }

    @Test
    fun retentionKeepsLatestPerArticleAndDropsExpiredFutureOrInvalidEntries() {
        val now = 2_000_000_000_000L
        val recent = ReaderPosition("https://publisher.example/a", 0.4f, now - 1_000L)
        val newer = recent.copy(progress = 0.7f, updatedAtMillis = now - 500L)
        val other = ReaderPosition("https://publisher.example/b", 0.2f, now - 2_000L)
        val expired = ReaderPosition(
            "https://publisher.example/old",
            0.6f,
            now - ReaderPositionPolicy.MAX_AGE_MILLIS - 1L
        )
        val future = ReaderPosition("https://publisher.example/future", 0.6f, now + 1L)
        val invalid = ReaderPosition("https://publisher.example/done", 0.99f, now - 100L)

        val retained = ReaderPositionPolicy.retain(
            listOf(recent, newer, other, expired, future, invalid),
            nowMillis = now
        )

        assertEquals(listOf(recent.articleUrl, other.articleUrl), retained.map { it.articleUrl })
        assertEquals(0.7f, retained.first().progress, 0.001f)
    }

    @Test
    fun retentionIsBoundedToTheMostRecentlyUpdatedArticles() {
        val now = 2_000_000_000_000L
        val positions = (0 until 5).map {
            ReaderPosition("https://publisher.example/$it", 0.3f, now - it * 1_000L)
        }
        val retained = ReaderPositionPolicy.retain(positions, nowMillis = now, maxEntries = 3)
        assertEquals(3, retained.size)
        assertEquals("https://publisher.example/0", retained.first().articleUrl)
    }
}
