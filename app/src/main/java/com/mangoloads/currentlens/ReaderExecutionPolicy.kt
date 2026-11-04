package com.mangoloads.currentlens

/**
 * JavaScript is limited to the sanitised, app-owned article document so its native word
 * selection bridge can read the selected text and geometry. A publisher host allowlist
 * constrains navigation; it does not make publisher-supplied scripts safe. Publisher
 * fallback uses Android's native PROCESS_TEXT integration instead.
 */
internal object ReaderExecutionPolicy {
    fun javascriptEnabled(
        hasSanitizedArticle: Boolean,
        hasAllowlistedPublisherFallback: Boolean
    ): Boolean = hasSanitizedArticle && !hasAllowlistedPublisherFallback
}
