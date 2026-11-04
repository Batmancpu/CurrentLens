package com.mangoloads.currentlens

/**
 * JavaScript is allowed only for the sanitised, app-owned article document or an explicitly
 * allow-listed publisher fallback. The sanitiser removes scripts, handlers and unsafe URLs
 * from extracted article markup before it reaches WebView.
 */
internal object ReaderExecutionPolicy {
    fun javascriptEnabled(
        hasSanitizedArticle: Boolean,
        hasAllowlistedPublisherFallback: Boolean
    ): Boolean = hasSanitizedArticle || hasAllowlistedPublisherFallback
}
