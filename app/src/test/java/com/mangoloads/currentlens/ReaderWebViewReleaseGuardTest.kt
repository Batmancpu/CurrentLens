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
            listOf("stop", "cancel-callbacks", "remove-parent", "remove-children", "destroy"),
            calls
        )
    }

    @Test
    fun fatalLinkageErrorsPropagateInsteadOfBeingSwallowed() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()
        var propagated = false

        try {
            guard.release(
                listOf(
                    { calls += "fatal"; throw LinkageError("synthetic fatal linkage failure") },
                    { calls += "must-not-run-after-fatal-error" }
                )
            )
        } catch (_: LinkageError) {
            propagated = true
        }

        assertEquals(true, propagated)
        assertEquals(listOf("fatal"), calls)
    }

    @Test
    fun multipleCleanupFailuresDoNotSkipIndependentReleaseSteps() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()

        guard.release(
            listOf(
                { calls += "remove-save-callback"; throw IllegalStateException("synthetic callback failure") },
                { calls += "clear-save-reference" },
                { calls += "remove-restore-callback"; throw IllegalStateException("synthetic restore failure") },
                { calls += "clear-restore-reference" },
                { calls += "clear-web-view-client" },
                { calls += "destroy" }
            )
        )

        assertEquals(
            listOf(
                "remove-save-callback",
                "clear-save-reference",
                "remove-restore-callback",
                "clear-restore-reference",
                "clear-web-view-client",
                "destroy"
            ),
            calls
        )
    }
}
