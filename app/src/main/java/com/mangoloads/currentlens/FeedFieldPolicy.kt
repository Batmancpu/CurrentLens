package com.mangoloads.currentlens

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

    fun titleSource(value: String): String = value.take(MAX_TITLE_SOURCE_CHARS)

    fun descriptionSource(value: String): String = value.take(MAX_DESCRIPTION_SOURCE_CHARS)

    fun url(value: String): String? = value.trim().takeIf {
        it.isNotEmpty() && it.length <= MAX_URL_CHARS
    }

    fun date(value: String): String? = value.trim().takeIf {
        it.isNotEmpty() && it.length <= MAX_DATE_CHARS
    }
}
