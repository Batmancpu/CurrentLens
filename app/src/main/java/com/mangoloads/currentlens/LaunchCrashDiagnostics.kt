package com.mangoloads.currentlens

import android.app.ActivityManager
import android.app.ApplicationExitInfo
import android.content.Context
import android.os.Build
import android.os.Process
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import kotlin.system.exitProcess

/**
 * Captures Java/Kotlin startup failures before AndroidX Startup providers are created.
 *
 * The report stays on-device. No crash information is uploaded or transmitted.
 */
internal object LaunchCrashDiagnostics {
    const val MAX_REPORT_CHARS = 30_000
    private const val MAX_STAGE_CHARS = 512
    private const val REPORT_NAME = "currentlens-last-crash.txt"
    private const val REPORT_TEMP_NAME = "currentlens-last-crash.tmp"
    private const val STAGE_NAME = "currentlens-last-startup-stage.txt"

    @Volatile
    private var installedHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * Install from Application.attachBaseContext(), which runs before ContentProviders
     * (including AndroidX Startup) and before any Activity is created.
     */
    fun install(context: Context) {
        synchronized(this) {
            val current = Thread.getDefaultUncaughtExceptionHandler()
            if (current != null && current === installedHandler) return

            val appContext = context.applicationContext ?: context
            val handler = Thread.UncaughtExceptionHandler { thread, throwable ->
                runCatching {
                    writeCrashReport(appContext, thread.name, throwable)
                }
                if (current != null) {
                    current.uncaughtException(thread, throwable)
                } else {
                    Process.killProcess(Process.myPid())
                    exitProcess(10)
                }
            }
            installedHandler = handler
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }
    }

    /**
     * Called from Application.attachBaseContext before ContentProviders start.
     * If the prior process never reached its first stable Compose frames, preserve
     * that fact even when Android killed it without a Java/Kotlin exception.
     */
    fun beginStartup(context: Context) {
        // Read the previous process state before recording anything for this process.
        // The 0.2.1-stability build wrote its own renderer stage before this check;
        // remove only the deterministic false-positive report produced by that order.
        runCatching {
            val directory = context.applicationContext?.filesDir ?: context.filesDir
            val stageFile = File(directory, STAGE_NAME)
            // Diagnostic files are local but may have been written by older builds.
            // Read only bounded prefixes on the startup path; oversized legacy reports
            // are disposable diagnostics and are removed without loading them into memory.
            val previousStage = readTextPrefix(stageFile, MAX_STAGE_CHARS)
            val reportFile = File(directory, REPORT_NAME)
            val reportLength = reportFile.takeIf { it.isFile }?.length() ?: 0L
            val existingReport = if (reportLength > MAX_REPORT_CHARS) {
                ""
            } else {
                readTextPrefix(reportFile, MAX_REPORT_CHARS).orEmpty()
            }
            if (reportFile.isFile && shouldDiscardExistingReport(reportLength, existingReport)) {
                // Remove pre-privacy diagnostic reports that exposed device/build identifiers.
                reportFile.delete()
            }

            val previousPid = processIdFromStage(previousStage)
            val exitInfo = findPreviousExitInfo(context, previousPid)
            val priorLaunchIncomplete = !previousStage.isNullOrBlank() &&
                !previousStage.contains("First Compose frames completed") &&
                !isLegacyMisorderedRendererStage(previousStage)
            val recentCrash = exitInfo?.let {
                val age = System.currentTimeMillis() - it.timestamp
                age in 0..(15 * 60 * 1000L) && isCrashLikeExit(it.reason)
            } == true

            if (!reportFile.isFile && (priorLaunchIncomplete || recentCrash)) {
                val stage = previousStage ?: "No previous startup stage was persisted"
                runCatching { writeInterruptedStartupReport(context, stage, exitInfo) }
            }
            recordStage(context, "Application.attachBaseContext completed")
        }
    }

    /** Record the last completed startup boundary for the next crash report. */
    fun recordStage(context: Context, stage: String) {
        if (stage.isBlank()) return
        runCatching {
            File((context.applicationContext ?: context).filesDir, STAGE_NAME)
                .writeText(
                    Instant.now().toString() + " | pid=" + Process.myPid() + " | " + stage.take(240),
                    Charsets.UTF_8
                )
        }
    }

    /** A launch is considered healthy only after the first several Compose frames. */
    fun markHealthy(
        context: Context,
        completedStage: String = "First Compose frames completed"
    ) {
        val directory = (context.applicationContext ?: context).filesDir
        runCatching { File(directory, REPORT_NAME).delete() }
        recordStage(context, completedStage)
    }

    fun read(context: Context): String? = runCatching {
        readTextPrefix(
            File((context.applicationContext ?: context).filesDir, REPORT_NAME),
            MAX_REPORT_CHARS
        )
    }.getOrNull()

    fun clear(context: Context): Boolean {
        val directory = (context.applicationContext ?: context).filesDir
        val reportDeleted = runCatching {
            val file = File(directory, REPORT_NAME)
            !file.exists() || file.delete()
        }.getOrDefault(false)
        val tempDeleted = runCatching { File(directory, REPORT_TEMP_NAME).delete() }.getOrDefault(false)
        runCatching { File(directory, STAGE_NAME).delete() }
        return reportDeleted && (tempDeleted || !File(directory, REPORT_TEMP_NAME).exists())
    }

    /** Read at most [maxChars] characters; never materialise an unbounded diagnostic file. */
    internal fun readTextPrefix(file: File, maxChars: Int): String? {
        if (maxChars < 0) return null
        return runCatching {
            file.inputStream().bufferedReader(Charsets.UTF_8).use { reader ->
                val buffer = CharArray(maxChars)
                val count = reader.read(buffer, 0, buffer.size)
                if (count <= 0) "" else String(buffer, 0, count)
            }
        }.getOrNull()
    }

    internal fun shouldDiscardExistingReport(reportLength: Long, report: String): Boolean =
        reportLength > MAX_REPORT_CHARS ||
            isKnownLegacyFalsePositive(report) ||
            hasLegacyDeviceMetadata(report)

    internal fun formatInterruptedStartupReport(
        lastCompletedStage: String,
        versionName: String? = null,
        packageName: String = "com.mangoloads.currentlens",
        systemExitReason: Int? = null,
        systemExitStatus: Int? = null
    ): String = buildString {
        appendLine("CurrentLens startup interruption report")
        appendLine("App: " + versionName.orEmpty())
        appendLine("Package: " + packageName)
        appendLine("Android API: " + Build.VERSION.SDK_INT)
        appendLine("Report type: startup interrupted; no uncaught Java/Kotlin exception was captured")
        appendLine("Last completed startup stage: " + displayStartupStage(lastCompletedStage))
        appendLine()
        val systemExitSummary = if (systemExitReason != null && systemExitStatus != null) {
            formatExitSummary(systemExitReason, systemExitStatus)
        } else {
            null
        }
        if (systemExitSummary != null) {
            appendLine("Android historical process-exit record:")
            appendLine(systemExitSummary)
        } else {
            appendLine("Android historical process-exit record: unavailable")
        }
        appendLine()
        appendLine("The previous process ended before confirming the first stable Compose frames.")
        appendLine("Use the Android process-exit reason above to distinguish a Java crash, native crash,")
        appendLine("ANR, signal, low-memory termination, or user/system stop.")
        appendLine("This report omits device identifiers, process identifiers, precise timestamps and raw traces.")
        appendLine("It is not a Java/Kotlin stack trace.")
    }.take(MAX_REPORT_CHARS)

    private fun writeInterruptedStartupReport(
        context: Context,
        lastCompletedStage: String,
        exitInfo: ApplicationExitInfo?
    ) {
        val versionName = runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull()
        writeReportFile(
            context.filesDir,
            formatInterruptedStartupReport(
                lastCompletedStage = lastCompletedStage,
                versionName = versionName,
                packageName = context.packageName,
                systemExitReason = exitInfo?.reason,
                systemExitStatus = exitInfo?.status
            )
        )
    }

    internal fun displayStartupStage(stage: String): String =
        stage.substringAfterLast("|").trim().take(240)

    internal fun hasLegacyDeviceMetadata(report: String): Boolean =
        report.contains("Device:") ||
            report.contains("Device code:") ||
            report.contains("Build fingerprint:") ||
            report.contains("Process id:") ||
            report.contains("Android system trace (Base64")

    internal fun processIdFromStage(stage: String?): Int? =
        stage?.split("|")?.getOrNull(1)?.trim()?.removePrefix("pid=")?.toIntOrNull()

    internal fun isLegacyMisorderedRendererStage(stage: String): Boolean {
        val hasPid = stage.split("|").any { it.trim().startsWith("pid=") }
        return !hasPid && stage.trimEnd().endsWith("Prismal pipeline forced to Standard")
    }

    internal fun isKnownLegacyFalsePositive(report: String): Boolean {
        val lines = report.lineSequence().toList()
        return lines.contains("CurrentLens startup interruption report") &&
            lines.contains(
                "Report type: startup interrupted; no uncaught Java/Kotlin exception was captured"
            ) &&
            lines.contains("App: 0.2.1-stability") &&
            lines.contains("Package: com.mangoloads.currentlens.stability") &&
            lines.any {
                it.startsWith("Last completed startup stage: ") &&
                    it.endsWith(" | Prismal pipeline forced to Standard")
            }
    }

    private fun findPreviousExitInfo(context: Context, previousPid: Int?): ApplicationExitInfo? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return null
        return runCatching {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                ?: return@runCatching null
            manager.getHistoricalProcessExitReasons(context.packageName, previousPid ?: 0, 5)
                .firstOrNull { previousPid == null || it.pid == previousPid }
        }.getOrNull()
    }

    private fun isCrashLikeExit(reason: Int): Boolean = when (reason) {
        ApplicationExitInfo.REASON_CRASH,
        ApplicationExitInfo.REASON_CRASH_NATIVE,
        ApplicationExitInfo.REASON_ANR,
        ApplicationExitInfo.REASON_INITIALIZATION_FAILURE,
        ApplicationExitInfo.REASON_SIGNALED -> true
        else -> false
    }

    /**
     * Export only the allow-listed numeric fields needed to distinguish an exit.
     * Android's free-form description can contain opaque system diagnostics (including
     * addresses); importance and memory measurements are unnecessary in a shareable report.
     */
    internal fun formatExitSummary(reason: Int, status: Int): String = buildString {
        appendLine("Reason: " + exitReasonName(reason) + " (" + reason + ")")
        append("Status/signal: " + status)
    }

    private fun exitReasonName(reason: Int): String = when (reason) {
        ApplicationExitInfo.REASON_UNKNOWN -> "UNKNOWN"
        ApplicationExitInfo.REASON_EXIT_SELF -> "EXIT_SELF"
        ApplicationExitInfo.REASON_SIGNALED -> "SIGNALED"
        ApplicationExitInfo.REASON_LOW_MEMORY -> "LOW_MEMORY"
        ApplicationExitInfo.REASON_CRASH -> "CRASH"
        ApplicationExitInfo.REASON_CRASH_NATIVE -> "CRASH_NATIVE"
        ApplicationExitInfo.REASON_ANR -> "ANR"
        ApplicationExitInfo.REASON_INITIALIZATION_FAILURE -> "INITIALIZATION_FAILURE"
        ApplicationExitInfo.REASON_PERMISSION_CHANGE -> "PERMISSION_CHANGE"
        ApplicationExitInfo.REASON_EXCESSIVE_RESOURCE_USAGE -> "EXCESSIVE_RESOURCE_USAGE"
        ApplicationExitInfo.REASON_USER_REQUESTED -> "USER_REQUESTED"
        ApplicationExitInfo.REASON_USER_STOPPED -> "USER_STOPPED"
        ApplicationExitInfo.REASON_DEPENDENCY_DIED -> "DEPENDENCY_DIED"
        ApplicationExitInfo.REASON_OTHER -> "OTHER"
        else -> "UNKNOWN_OR_NEWER_REASON"
    }

    private fun writeCrashReport(context: Context, threadName: String, throwable: Throwable) {
        val directory = context.filesDir
        val stage = readTextPrefix(File(directory, STAGE_NAME), MAX_STAGE_CHARS)
        val versionName = runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull()
        val report = formatReport(
            threadName = threadName,
            throwable = throwable,
            lastCompletedStage = stage,
            versionName = versionName,
            packageName = context.packageName
        )
        writeReportFile(directory, report)
    }

    private fun writeReportFile(directory: File, report: String) {
        val destination = File(directory, REPORT_NAME)
        val temporary = File(directory, REPORT_TEMP_NAME)

        // Flush the report to storage before delegating to Android's process-kill handler.
        FileOutputStream(temporary, false).use { stream ->
            stream.write(report.toByteArray(Charsets.UTF_8))
            stream.fd.sync()
        }
        if (!temporary.renameTo(destination)) {
            FileOutputStream(destination, false).use { stream ->
                stream.write(report.toByteArray(Charsets.UTF_8))
                stream.fd.sync()
            }
            temporary.delete()
        }
    }

    internal fun formatReport(
        threadName: String,
        throwable: Throwable,
        lastCompletedStage: String? = null,
        versionName: String? = null,
        packageName: String = "com.mangoloads.currentlens"
    ): String = buildString {
        appendLine("CurrentLens local startup crash report")
        appendLine("App: " + versionName.orEmpty())
        appendLine("Package: " + packageName)
        appendLine("Android API: " + Build.VERSION.SDK_INT)
        appendLine("Thread: " + threadName.take(40))
        appendLine("Last completed startup stage: " +
            (lastCompletedStage?.let(::displayStartupStage) ?: "unknown"))
        appendLine("Exception type: " + throwable.javaClass.name)
        appendLine("Stack frames (class and method only):")
        throwable.stackTrace.take(20).forEach { frame ->
            appendLine("  at " + frame.className + "." + frame.methodName)
        }
    }.take(MAX_REPORT_CHARS)
}
