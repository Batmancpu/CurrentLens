package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

class ReaderDocumentTest {
    @Test fun documentEscapesMetadataAndPreservesAccessibleViewport() {
        val article = Article(id = "fixture", title = "Inflation <script>",
            description = "RBI monetary policy", url = "https://example.test/fixture",
            source = "Test & news", section = "Economy", published = "2026-11-24T10:00:00Z")
        val html = readerDocument(article, "<p>Sanitised content</p>", 28f, true)
        assertTrue(html.contains("lang=\"en\""))
        assertTrue(html.contains("Inflation &lt;script&gt;"))
        assertTrue(html.contains("Test &amp; news"))
        assertTrue(html.contains("font-size:28.0px"))
        assertTrue(html.contains("text-decoration:underline"))
        assertTrue(html.contains("<main><p>Sanitised content</p></main>"))
        assertFalse(html.contains("user-scalable=no"))
        assertFalse(html.contains("<script>"))
    }
}
