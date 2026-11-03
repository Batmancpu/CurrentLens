package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ArticleReaderSanitizerTest {
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
    }
}
