package com.mangoloads.currentlens

/** App-owned HTML document, separate from Compose/WebView lifecycle. Content is already sanitized. */
internal fun readerDocument(article: Article, content: String, fontSizeCssPx: Float, dark: Boolean): String {
    val background = if (dark) "#10141B" else "#FFFFFF"
    val foreground = if (dark) "#E9EDF5" else "#20242B"
    val muted = if (dark) "#AAB4C5" else "#717985"
    val title = ReaderPresentationPolicy.escapeHtmlText(article.title)
    val source = ReaderPresentationPolicy.escapeHtmlText(article.source)
    val published = ReaderPresentationPolicy.escapeHtmlText(ArticlePublicationDatePolicy.label(article.publishedAtMillis))
    val link = if (dark) "#9CB9FF" else "#2859A8"
    val quoteBackground = if (dark) "#171D27" else "#F2F5F9"
    val quoteBorder = if (dark) "#52658D" else "#A9BCE0"
    return buildString {
        append("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\">")
        // Keep pinch zoom available for readers who use it as an accessibility aid.
        append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><style>")
        append("html{background:$background;color:$foreground;}body{max-width:860px;margin:0 auto;padding:24px 22px 56px;background:$background;color:$foreground;font-family:-apple-system,BlinkMacSystemFont,'SF Pro Text','Helvetica Neue',system-ui,sans-serif;font-size:${fontSizeCssPx}px;line-height:1.7;-webkit-text-size-adjust:100%;}")
        append(".cl-source{font:700 0.68em/1.45 system-ui,sans-serif;letter-spacing:1.1px;text-transform:uppercase;color:$muted;margin:2px 0 10px;}")
        append(".cl-date{font:500 0.78em/1.5 system-ui,sans-serif;color:$muted;margin:0 0 22px;}")
        append("h1{max-width:22ch;font:700 1.76em/1.1 -apple-system,BlinkMacSystemFont,'SF Pro Display',system-ui,sans-serif;letter-spacing:-.035em;overflow-wrap:anywhere;margin:0 0 12px;color:$foreground;}")
        append("main{font-family:Georgia,'Times New Roman',serif;}")
        append("h2,h3{font-family:system-ui,sans-serif;line-height:1.25;margin:1.55em 0 .55em;}p,li{margin:0 0 1.05em;}img,figure,video{max-width:100%;height:auto;border-radius:14px;}figure{margin:1.4em 0;}figcaption{font:.75em/1.5 system-ui,sans-serif;color:$muted;}")
        append("a{color:$link;text-decoration:underline;text-underline-offset:.14em;}blockquote{margin:1.4em 0;padding:14px 16px;border-left:3px solid $quoteBorder;border-radius:0 12px 12px 0;background:$quoteBackground;color:$muted;}")
        append(ReaderPresentationPolicy.editorialContentCss(dark))
        append(ReaderPresentationPolicy.responsiveTableCss(dark))
        append("iframe{max-width:100%;}pre{white-space:pre-wrap;overflow-wrap:anywhere;border-radius:12px;padding:12px;background:$quoteBackground;}code{overflow-wrap:anywhere;}")
        append("</style></head><body>")
        append("<header class=\"cl-article-header\"><div class=\"cl-source\">$source · brrrNews Reader</div>")
        append("<h1>$title</h1><p class=\"cl-date\">$published</p></header>")
        append("<main>$content</main>")
        append(ReaderPresentationPolicy.studyContextHtml(UpscEngine.classify(article)))
        append("</body></html>")
    }
}
