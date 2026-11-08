package com.mangoloads.currentlens

/**
 * Runs WebView teardown once and continues best-effort cleanup if an earlier step
 * throws. The caller supplies actions in ownership order, ending with destroy().
 */
internal class ReaderWebViewReleaseGuard {
    private var released = false

    fun release(cleanup: List<() -> Unit>) {
        if (released) return
        released = true
        cleanup.forEach { action ->
            runCatching { action() }
        }
    }
}
