package com.mangoloads.currentlens

import java.util.Locale

/**
 * Bounds individual values extracted from untrusted RSS/Atom entries.
 *
 * The response byte ceiling limits the XML document, but a single field can still
 * consume most of that document and trigger avoidable HTML-normalisation allocations.
 * Keep only the amount of text the app can use, and reject overlong identifiers rather
 * than truncating them into a different URL or timestamp.
 */
internal object FeedFieldPolicy {
    const val MAX_TITLE_SOURCE_CHARS = 512
    const val MAX_DESCRIPTION_SOURCE_CHARS = 8_192
    const val MAX_URL_CHARS = 2_048
    const val MAX_DATE_CHARS = 256

    private const val ATOM_NAMESPACE = "http://www.w3.org/2005/Atom"
    private val atomInlineTextTypes = setOf(
        "text", "html", "xhtml",
        "text/plain", "text/html", "application/xhtml+xml"
    )

    /**
     * Atom content can carry inline text/html/xhtml, while Media RSS content usually
     * carries a URL to an image. Never fetch Atom's external src here; only read inline
     * Atom text constructs and leave URL-backed media to the image path.
     */
    fun isInlineAtomTextContent(
        namespace: String?,
        type: String?,
        src: String?,
        url: String?
    ): Boolean {
        if (namespace != ATOM_NAMESPACE || !src.isNullOrBlank() || !url.isNullOrBlank()) {
            return false
        }
        val mediaType = type?.substringBefore(';')?.trim()?.lowercase(Locale.ROOT)
        return mediaType.isNullOrBlank() || mediaType in atomInlineTextTypes
    }

    fun titleSource(value: String): String = boundedPrefix(value, MAX_TITLE_SOURCE_CHARS)

    fun descriptionSource(value: String): String =
        boundedPrefix(value, MAX_DESCRIPTION_SOURCE_CHARS)

    fun url(value: String): String? {
        if (value.length > MAX_URL_CHARS) return null
        return value.trim().takeIf { it.isNotEmpty() }
    }

    fun date(value: String): String? {
        if (value.length > MAX_DATE_CHARS) return null
        return value.trim().takeIf { it.isNotEmpty() }
    }

    /** Avoid leaving an unmatched high surrogate at a UTF-16 truncation boundary. */
    private fun boundedPrefix(value: String, maxChars: Int): String {
        if (value.length <= maxChars) return value
        val end = if (maxChars > 0 && Character.isHighSurrogate(value[maxChars - 1])) {
            maxChars - 1
        } else {
            maxChars
        }
        return value.substring(0, end)
    }
}
