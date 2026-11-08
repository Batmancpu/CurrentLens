package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FeedFieldPolicyTest {
    @Test
    fun titleAndDescriptionInputsAreBoundedBeforeHtmlNormalisation() {
        assertEquals(
            FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS,
            FeedFieldPolicy.titleSource("t".repeat(FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS + 1)).length
        )
        assertEquals(
            FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS,
            FeedFieldPolicy.descriptionSource("d".repeat(FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS + 1)).length
        )
    }

    @Test
    fun shortTextIsPreservedWithoutTruncation() {
        assertEquals("A policy headline", FeedFieldPolicy.titleSource("A policy headline"))
        assertEquals("A useful RSS excerpt", FeedFieldPolicy.descriptionSource("A useful RSS excerpt"))
    }

    @Test
    fun urlIsTrimmedButNeverTruncated() {
        assertEquals("https://example.org/story", FeedFieldPolicy.url("  https://example.org/story  "))
        assertNull(FeedFieldPolicy.url(" "))
        assertNull(FeedFieldPolicy.url("https://example.org/" + "x".repeat(FeedFieldPolicy.MAX_URL_CHARS)))
        assertEquals(
            FeedFieldPolicy.MAX_URL_CHARS,
            requireNotNull(FeedFieldPolicy.url("https://e.org/" + "x".repeat(FeedFieldPolicy.MAX_URL_CHARS - "https://e.org/".length))).length
        )
    }

    @Test
    fun dateIsTrimmedAndOverlongValuesAreRejected() {
        assertEquals("Tue, 03 Nov 2026 10:00:00 +0530", FeedFieldPolicy.date(" Tue, 03 Nov 2026 10:00:00 +0530 "))
        assertNull(FeedFieldPolicy.date(""))
        assertNull(FeedFieldPolicy.date("x".repeat(FeedFieldPolicy.MAX_DATE_CHARS + 1)))
    }
}
