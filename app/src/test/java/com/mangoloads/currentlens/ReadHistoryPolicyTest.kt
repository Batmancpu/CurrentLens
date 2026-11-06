package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ReadHistoryPolicyTest {
    @Test
    fun addsNewUrlWithoutDroppingExistingMarkers() {
        val existing = setOf("https://example.test/old")
        assertEquals(
            setOf("https://example.test/old", "https://example.test/new"),
            ReadHistoryPolicy.add(existing, "https://example.test/new")
        )
    }

    @Test
    fun duplicateUrlIsIdempotent() {
        val existing = setOf("https://example.test/story")
        assertSame(existing, ReadHistoryPolicy.add(existing, "https://example.test/story"))
    }

    @Test
    fun blankUrlDoesNotCreateAReadMarker() {
        val existing = setOf("https://example.test/story")
        assertSame(existing, ReadHistoryPolicy.add(existing, "  "))
    }
}
