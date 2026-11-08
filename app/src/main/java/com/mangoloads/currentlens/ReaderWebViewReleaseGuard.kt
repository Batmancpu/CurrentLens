package com.mangoloads.currentlens

/**
 * Runs WebView teardown once and continues best-effort cleanup if an earlier step
 * throws an ordinary exception. Fatal VM/linkage errors are allowed to propagate.
 * The caller supplies actions in ownership order, ending with destroy().
 */
internal class ReaderWebViewReleaseGuard {
    private var released = false

    fun release(cleanup: List<() -> Unit>) {
        if (released) return
        released = true
        cleanup.forEach { action ->
            try {
                action()
            } catch (_: Exception) {
                // Continue releasing later resources after an ordinary cleanup failure.
            }
        }
    }
}
