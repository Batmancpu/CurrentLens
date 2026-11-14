package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
    fun textBoundsDoNotSplitSupplementaryCharacters() {
        val title = "a".repeat(FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS - 1) + "😀tail"
        val description = "d".repeat(FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS - 1) + "😀tail"

        assertEquals(
            "a".repeat(FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS - 1),
            FeedFieldPolicy.titleSource(title)
        )
        assertEquals(
            "d".repeat(FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS - 1),
            FeedFieldPolicy.descriptionSource(description)
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
    @Test
    fun inlineAtomContentRecognisesTextHtmlAndXhtmlConstructs() {
        val atom = "http://www.w3.org/2005/Atom"
        listOf(
            null,
            "text",
            "html",
            "xhtml",
            "text/plain",
            "text/html",
            "application/xhtml+xml",
            "text/html; charset=utf-8"
        ).forEach { type ->
            assertTrue(
                "Expected inline Atom text type to be accepted: $type",
                FeedFieldPolicy.isInlineAtomTextContent(
                    namespace = atom,
                    type = type,
                    src = null,
                    url = null
                )
            )
        }
    }

    @Test
    fun externalAtomContentAndMediaRssImagesAreNotReadAsInlineText() {
        val atom = "http://www.w3.org/2005/Atom"
        assertFalse(
            FeedFieldPolicy.isInlineAtomTextContent(
                namespace = atom,
                type = "text",
                src = "https://publisher.example/content/123",
                url = null
            )
        )
        assertFalse(
            FeedFieldPolicy.isInlineAtomTextContent(
                namespace = atom,
                type = "image/png",
                src = null,
                url = null
            )
        )
        assertFalse(
            FeedFieldPolicy.isInlineAtomTextContent(
                namespace = "http://search.yahoo.com/mrss/",
                type = "image/jpeg",
                src = null,
                url = "https://publisher.example/image.jpg"
            )
        )
    }

}
