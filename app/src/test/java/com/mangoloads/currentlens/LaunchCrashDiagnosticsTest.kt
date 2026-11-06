package com.mangoloads.currentlens

import org.junit.Assert.assertTrue
import org.junit.Test

class LaunchCrashDiagnosticsTest {
    @Test
    fun reportContainsDeviceThreadExceptionAndStartupStage() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("first composition failed"),
            lastCompletedStage = "Application.attachBaseContext completed",
            versionName = "0.2.0"
        )

        assertTrue(report.contains("CurrentLens local startup crash report"))
        assertTrue(report.contains("Thread: main"))
        assertTrue(report.contains("IllegalStateException"))
        assertTrue(report.contains("first composition failed"))
        assertTrue(report.contains("Application.attachBaseContext completed"))
        assertTrue(report.contains("0.2.0"))
    }

    @Test
    fun interruptionReportDoesNotPretendToContainAnExceptionStackTrace() {
        val report = LaunchCrashDiagnostics.formatInterruptedStartupReport(
            lastCompletedStage = "MainActivity.setContent returned",
            versionName = "0.2.0"
        )

        assertTrue(report.contains("startup interrupted"))
        assertTrue(report.contains("no uncaught Java/Kotlin exception was captured"))
        assertTrue(report.contains("MainActivity.setContent returned"))
        assertTrue(report.contains("It is not a Java/Kotlin stack trace."))
    }

    @Test
    fun reportIsBoundedForSafeLocalDisplay() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("x".repeat(60_000))
        )
        assertTrue(report.length <= LaunchCrashDiagnostics.MAX_REPORT_CHARS)
    }
}
