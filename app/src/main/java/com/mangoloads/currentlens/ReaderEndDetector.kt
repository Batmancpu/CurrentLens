package com.mangoloads.currentlens

/** Pure near-end calculation shared by WebView scroll and short-page swipe handling. */
internal object ReaderEndDetector {
    fun isAtEnd(fullContentHeight: Int, viewportHeight: Int, scrollY: Int, tolerancePx: Int = 100): Boolean {
        if (fullContentHeight <= 0 || viewportHeight <= 0 || scrollY < 0 || tolerancePx < 0) return false
        return scrollY + viewportHeight >= fullContentHeight - tolerancePx
    }

    /** True only when the completed article is wholly visible without requiring a scroll. */
    fun isShortPageFullyVisible(fullContentHeight: Int, viewportHeight: Int): Boolean {
        if (fullContentHeight <= 0 || viewportHeight <= 0) return false
        return fullContentHeight <= viewportHeight
    }
}
