package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LaunchCrashDiagnosticsTest {
    @Test
    fun reportContainsExceptionAndStartupStageWithoutDeviceMetadata() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("first composition failed"),
            lastCompletedStage = "Application.attachBaseContext completed",
            versionName = "0.2.0"
        )

        assertTrue(report.contains("CurrentLens local startup crash report"))
        assertTrue(report.contains("Thread: main"))
        assertTrue(report.contains("IllegalStateException"))
        assertFalse(report.contains("first composition failed"))
        assertTrue(report.contains("Application.attachBaseContext completed"))
        assertTrue(report.contains("0.2.0"))
        assertFalse(report.contains("Device:"))
        assertFalse(report.contains("Build fingerprint:"))
        assertFalse(report.contains("Timestamp:"))
        assertFalse(report.contains("Process id:"))
    }

    @Test
    fun reportUsesActualInstalledPackageForSideBySideStabilityBuild() {
        val report = LaunchCrashDiagnostics.formatReport(
            threadName = "main",
            throwable = IllegalStateException("startup failure"),
            packageName = "com.mangoloads.currentlens.stability"
        )
        assertTrue(report.contains("Package: com.mangoloads.currentlens.stability"))
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
    fun interruptionReportIncludesExitReasonButOmitsRawTrace() {
        val report = LaunchCrashDiagnostics.formatInterruptedStartupReport(
            lastCompletedStage = "MainActivity.onCreate entered",
            systemExitSummary = "Reason: CRASH_NATIVE (5)"
        )

        assertTrue(report.contains("Android historical process-exit record:"))
        assertTrue(report.contains("Reason: CRASH_NATIVE"))
        assertFalse(report.contains("AQID"))
        assertFalse(report.contains("Timestamp:"))
        assertFalse(report.contains("Process id:"))
    }

    @Test
    fun startupStageIsSanitizedBeforeItIsDisplayed() {
        val stage = "2026-11-06T12:00:00Z | pid=29860 | MainActivity.onCreate entered"
        assertEquals("MainActivity.onCreate entered", LaunchCrashDiagnostics.displayStartupStage(stage))
    }

    @Test
    fun legacyReportsWithDeviceIdentifiersAreDetectedForCleanup() {
        val report = "Device: Example Brand Model\\nBuild fingerprint: private-build"
        assertTrue(LaunchCrashDiagnostics.hasLegacyDeviceMetadata(report))
        assertFalse(LaunchCrashDiagnostics.hasLegacyDeviceMetadata("Android API: 35\\nReason: CRASH_NATIVE"))
    }

    @Test
    fun newStartupStageStoresPidAndCanBeParsed() {
        val stage = "2026-11-06T12:00:00Z | pid=29860 | MainActivity.onCreate entered"
        assertEquals(29860, LaunchCrashDiagnostics.processIdFromStage(stage))
    }

    @Test
    fun legacyRendererStageWithoutPidIsRecognizedAsMisordered() {
        val legacy = "2026-11-06T12:00:00Z | Prismal pipeline forced to Standard"
        val current = "2026-11-06T12:00:00Z | pid=29860 | Prismal pipeline forced to Standard"

        assertTrue(LaunchCrashDiagnostics.isLegacyMisorderedRendererStage(legacy))
        assertFalse(LaunchCrashDiagnostics.isLegacyMisorderedRendererStage(current))
    }

    @Test
    fun onlyKnown021RendererReportIsSuppressed() {
        val report = """
            CurrentLens startup interruption report
            App: 0.2.1-stability
            Package: com.mangoloads.currentlens.stability
            Report type: startup interrupted; no uncaught Java/Kotlin exception was captured
            Last completed startup stage: 2026-11-06T12:57:34.848827Z | Prismal pipeline forced to Standard
        """.trimIndent()
        val unrelated = report.replace("0.2.1-stability", "0.2.2-stability")

        val genuineExceptionReport = report
            .replace("CurrentLens startup interruption report", "CurrentLens local startup crash report")
            .replace(
                "Report type: startup interrupted; no uncaught Java/Kotlin exception was captured",
                "Exception: java.lang.IllegalStateException"
            )

        assertTrue(LaunchCrashDiagnostics.isKnownLegacyFalsePositive(report))
        assertFalse(LaunchCrashDiagnostics.isKnownLegacyFalsePositive(unrelated))
        assertFalse(LaunchCrashDiagnostics.isKnownLegacyFalsePositive(genuineExceptionReport))
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
