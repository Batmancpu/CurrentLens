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


/**
 * Avoids declaring a short article complete while WebView is still reporting transient
 * content/viewport geometry during the first layout and resource reflow.
 */
internal class ReaderShortPageStabilityGate(
    private val requiredStableSamples: Int = 3
) {
    init {
        require(requiredStableSamples >= 2) { "At least two stable samples are required" }
    }

    private var lastFullContentHeight: Int? = null
    private var lastViewportHeight: Int? = null
    private var stableSamples = 0

    fun record(fullContentHeight: Int, viewportHeight: Int): Boolean {
        if (!ReaderEndDetector.isShortPageFullyVisible(fullContentHeight, viewportHeight)) {
            reset()
            return false
        }

        if (lastFullContentHeight == fullContentHeight && lastViewportHeight == viewportHeight) {
            stableSamples += 1
        } else {
            lastFullContentHeight = fullContentHeight
            lastViewportHeight = viewportHeight
            stableSamples = 1
        }
        return stableSamples >= requiredStableSamples
    }

    fun reset() {
        lastFullContentHeight = null
        lastViewportHeight = null
        stableSamples = 0
    }
}
