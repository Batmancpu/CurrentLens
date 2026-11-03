package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderProgressGateTest {
    @Test
    fun endSignalIsOncePerArticleAndResetsForTheNextArticle() {
        val gate = ReaderProgressGate()

        assertTrue(gate.beginArticle("article-a"))
        assertTrue(gate.markEndReached())
        assertFalse(gate.markEndReached())

        assertFalse(gate.beginArticle("article-a"))
        assertFalse(gate.markEndReached())

        assertTrue(gate.beginArticle("article-b"))
        assertTrue(gate.markEndReached())
        assertFalse(gate.markEndReached())
    }
}
