package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ArticleReaderSanitizerTest {
    @Test
    fun preservesArticleTablesAndTheirSemanticHeaders() {
        val readable = "A public article table row describing the route, service and travel distance. ".repeat(10)
        val html = """
            <table>
              <thead><tr><th>Train No.</th><th>From - To</th><th>Distance (Km)</th></tr></thead>
              <tbody><tr><td>26655/656</td><td>$readable</td><td>670</td></tr></tbody>
            </table>
        """.trimIndent()

        val cleaned = sanitizeArticleHtml(html, "https://publisher.example/story")

        assertNotNull(cleaned)
        assertTrue(cleaned.orEmpty().contains("<div class=\"cl-table-scroll\">"))
        assertTrue(cleaned.orEmpty().contains("<table>"))
        assertTrue(cleaned.orEmpty().contains("<th>Train No.</th>"))
        assertTrue(cleaned.orEmpty().contains("<td>26655/656</td>"))
    }

    @Test
    fun stripsExecutableMarkupAndUnsafeAttributesWhileKeepingArticleText() {
        val readable = "This is a publicly accessible article paragraph about institutions, policy and public administration. ".repeat(9)
        val hostile = """
            <p onclick="steal()">$readable</p>
            <script>window.compromised = true;</script>
            <a href="javascript:alert(1)">unsafe link</a>
            <img src="https://publisher.example/image.jpg" onerror="steal()" />
        """.trimIndent()

        val cleaned = sanitizeArticleHtml(hostile, "https://publisher.example/story")

        assertNotNull(cleaned)
        val html = cleaned.orEmpty().lowercase()
        assertTrue(html.contains("publicly accessible article"))
        assertFalse(html.contains("<script"))
        assertFalse(html.contains("onclick"))
        assertFalse(html.contains("onerror"))
        assertFalse(html.contains("javascript:"))

    @Test
    fun wrapsThreeColumnTablesAfterSanitizingPublisherMarkup() {
        val readable = "A public article table row describing train services and the route distance. ".repeat(10)
        val html = """
            <table>
              <thead><tr>
                <th scope="col">Train No.</th>
                <th scope="col">From - To</th>
                <th scope="col">Distance (Km)</th>
              </tr></thead>
              <tbody><tr>
                <td>26655/656</td><td>$readable</td><td>670</td>
              </tr></tbody>
            </table>
        """.trimIndent()

        val cleaned = sanitizeArticleHtml(html, "https://publisher.example/story")

        assertNotNull(cleaned)
        assertTrue(cleaned.orEmpty().contains("<div class=\"cl-table-scroll\">"))
        assertTrue(cleaned.orEmpty().contains("<th scope=\"col\">Train No.</th>"))
        assertTrue(cleaned.orEmpty().contains("<th scope=\"col\">From - To</th>"))
        assertTrue(cleaned.orEmpty().contains("<th scope=\"col\">Distance (Km)</th>"))
        assertTrue(cleaned.orEmpty().contains("<td>26655/656</td>"))
    }
    }
}
