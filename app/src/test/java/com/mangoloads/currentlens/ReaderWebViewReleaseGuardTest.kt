package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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


    @Test
    fun multipleOrdinaryCleanupFailuresDoNotSkipIndependentReleaseSteps() {
        val guard = ReaderWebViewReleaseGuard()
        val calls = mutableListOf<String>()

        guard.release(
            listOf(
                { calls += "remove-save-callback"; error("synthetic save cleanup failure") },
                { calls += "clear-save-reference" },
                { calls += "remove-restore-callback"; error("synthetic restore cleanup failure") },
                { calls += "clear-restore-reference" },
                { calls += "clear-web-view-client" },
                { calls += "destroy" }
            )
        )

        assertEquals(
            listOf(
                "remove-save-callback", "clear-save-reference",
                "remove-restore-callback", "clear-restore-reference",
                "clear-web-view-client", "destroy"
            ),
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

        assertTrue(propagated)
        assertEquals(listOf("fatal"), calls)
    }

}
