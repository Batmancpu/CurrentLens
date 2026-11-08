package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ReaderWebViewReleaseGuardTest {
    @Test
    fun releaseRunsCleanupInOrderOnlyOnce() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()
        val cleanup = listOf<() -> Unit>(
            { calls += "stop" },
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
            listOf("stop", "cancel-callbacks", "clear-client-closures", "clear-history", "remove-parent", "remove-children", "destroy"),
            calls
        )
    }

    @Test
    fun failedCleanupDoesNotSkipLaterReleaseSteps() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()

        guard.release(
            listOf(
                { calls += "stop" },
                { calls += "cancel-callbacks"; error("synthetic cleanup failure") },
                { calls += "remove-parent" },
                { calls += "remove-children" },
                { calls += "destroy" }
            )
        )

        assertEquals(
            listOf("stop", "cancel-callbacks", "remove-children", "destroy"),
            calls
        )
    }
}
