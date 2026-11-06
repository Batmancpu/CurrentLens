package com.mangoloads.currentlens

import org.junit.Assert.assertTrue
import org.junit.Test

class LaunchCrashDiagnosticsTest {
    @Test
    fun reportContainsDeviceThreadAndExceptionDetails() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("first composition failed")
        )

        assertTrue(report.contains("CurrentLens local crash report"))
        assertTrue(report.contains("Thread: main"))
        assertTrue(report.contains("IllegalStateException"))
        assertTrue(report.contains("first composition failed"))
    }

    @Test
    fun reportIsBoundedForSafeLocalDisplay() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("x".repeat(60_000))
        )
        assertTrue(report.length <= 30_000)
    }
}
