package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ReaderWebViewReleaseGuardTest {
    @Test
    fun releaseRunsCleanupInOrderOnlyOnce() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()
        val cleanup = listOf<() -> Unit>(
            { calls += "flush-position" },
            { calls += "stop-loading" },
            { calls += "cancel-callbacks" },
            { calls += "clear-client-closures" },
            { calls += "clear-history" },
            { calls += "remove-parent" },
            { calls += "remove-children" },
            { calls += "destroy" }
        )

        guard.release(cleanup)
        guard.release(cleanup)

        assertEquals(
            listOf(
                "flush-position", "stop-loading", "cancel-callbacks",
                "clear-client-closures", "clear-history", "remove-parent",
                "remove-children", "destroy"
            ),
            calls
        )
    }

    @Test
    fun failedCleanupDoesNotSkipLaterStepsOrRetryTheRelease() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()
        val cleanup = listOf<() -> Unit>(
            { calls += "stop-loading" },
            { calls += "cancel-callbacks" },
            { calls += "remove-parent"; error("synthetic detach failure") },
            { calls += "remove-children" },
            { calls += "destroy" }
        )

        guard.release(cleanup)
        guard.release(cleanup)

        assertEquals(
            listOf("stop-loading", "cancel-callbacks", "remove-parent", "remove-children", "destroy"),
            calls
        )
    }
}
