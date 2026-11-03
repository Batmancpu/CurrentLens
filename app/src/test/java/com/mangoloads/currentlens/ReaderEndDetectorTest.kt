package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderEndDetectorTest {
    @Test
    fun detectsNearBottomOfScrollableArticle() {
        assertFalse(ReaderEndDetector.isAtEnd(fullContentHeight = 2400, viewportHeight = 900, scrollY = 1200))
        assertTrue(ReaderEndDetector.isAtEnd(fullContentHeight = 2400, viewportHeight = 900, scrollY = 1420))
    }

    @Test
    fun shortArticleCountsAsAtEndAfterAnUpwardSwipeAttempt() {
        assertTrue(ReaderEndDetector.isAtEnd(fullContentHeight = 650, viewportHeight = 900, scrollY = 0))
    }

    @Test
    fun refusesInvalidGeometry() {
        assertFalse(ReaderEndDetector.isAtEnd(fullContentHeight = 0, viewportHeight = 900, scrollY = 0))
        assertFalse(ReaderEndDetector.isAtEnd(fullContentHeight = 500, viewportHeight = 0, scrollY = 0))
        assertFalse(ReaderEndDetector.isAtEnd(fullContentHeight = 500, viewportHeight = 900, scrollY = -1))
    }
}
