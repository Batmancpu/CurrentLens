package com.mangoloads.currentlens

import android.app.ActivityManager
import android.app.ApplicationExitInfo
import android.content.Context
import android.os.Build
import android.os.Process
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.time.Instant
import kotlin.system.exitProcess

/**
 * Captures Java/Kotlin startup failures before AndroidX Startup providers are created.
 *
 * The report stays on-device. No crash information is uploaded or transmitted.
 */
internal object LaunchCrashDiagnostics {
    const val MAX_REPORT_CHARS = 30_000
    private const val MAX_TRACE_BYTES = 8192
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
            val previousStage = runCatching {
                stageFile.takeIf { it.isFile }?.readText(Charsets.UTF_8)
            }.getOrNull()
            val reportFile = File(directory, REPORT_NAME)
            if (reportFile.isFile && isKnownLegacyFalsePositive(
                    runCatching { reportFile.readText(Charsets.UTF_8) }.getOrDefault("")
                )
            ) {
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
    fun markHealthy(context: Context) {
        val directory = (context.applicationContext ?: context).filesDir
        runCatching { File(directory, REPORT_NAME).delete() }
        recordStage(context, "First Compose frames completed")
    }

    fun read(context: Context): String? = runCatching {
        File((context.applicationContext ?: context).filesDir, REPORT_NAME)
            .takeIf { it.isFile }
            ?.readText(Charsets.UTF_8)
            ?.take(MAX_REPORT_CHARS)
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

    internal fun formatInterruptedStartupReport(
        lastCompletedStage: String,
        versionName: String? = null,
        processId: Int? = null,
        packageName: String = "com.mangoloads.currentlens",
        systemExitSummary: String? = null,
        nativeTraceBase64: String? = null
    ): String = buildString {
        appendLine("CurrentLens startup interruption report")
        appendLine("Timestamp: " + Instant.now().toString())
        appendLine("App: " + versionName.orEmpty())
        appendLine("Package: " + packageName)
        appendLine("Device: " + Build.MANUFACTURER + " " + Build.BRAND + " " + Build.MODEL)
        appendLine("Device code: " + Build.DEVICE)
        appendLine("Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")")
        appendLine("Build fingerprint: " + Build.FINGERPRINT)
        appendLine("Process id: " + (processId?.toString() ?: "unavailable"))
        appendLine("Report type: startup interrupted; no uncaught Java/Kotlin exception was captured")
        appendLine("Last completed startup stage: " + lastCompletedStage)
        appendLine()
        if (!systemExitSummary.isNullOrBlank()) {
            appendLine("Android historical process-exit record:")
            appendLine(systemExitSummary)
        } else {
            appendLine("Android historical process-exit record: unavailable")
        }
        if (!nativeTraceBase64.isNullOrBlank()) {
            appendLine()
            appendLine("Android system trace (Base64; first 8192 bytes only):")
            appendLine(nativeTraceBase64)
        }
        appendLine()
        appendLine("The previous process ended before confirming the first stable Compose frames.")
        appendLine("Use the Android process-exit reason above to distinguish a Java crash, native crash,")
        appendLine("ANR, signal, low-memory termination, or user/system stop.")
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
                processId = Process.myPid(),
                packageName = context.packageName,
                systemExitSummary = exitInfo?.let(::describeExitInfo),
                nativeTraceBase64 = exitInfo?.let(::readExitTraceBase64)
            )
        )
    }

    private fun processIdFromStage(stage: String?): Int? =
        stage?.split("|")?.getOrNull(1)?.trim()?.removePrefix("pid=")?.toIntOrNull()

    private fun isLegacyMisorderedRendererStage(stage: String): Boolean {
        val hasPid = stage.split("|").any { it.trim().startsWith("pid=") }
        return !hasPid && stage.trimEnd().endsWith("Prismal pipeline forced to Standard")
    }

    private fun isKnownLegacyFalsePositive(report: String): Boolean {
        val lines = report.lineSequence().toList()
        return lines.contains("App: 0.2.1-stability") &&
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

    private fun describeExitInfo(info: ApplicationExitInfo): String = buildString {
        appendLine("Reason: " + exitReasonName(info.reason) + " (" + info.reason + ")")
        appendLine("Timestamp: " + Instant.ofEpochMilli(info.timestamp).toString())
        appendLine("PID: " + info.pid + " | Process: " + info.processName)
        appendLine("Status/signal: " + info.status)
        appendLine("Importance: " + info.importance)
        appendLine("PSS/RSS: " + info.pss + " KB / " + info.rss + " KB")
        if (!info.description.isNullOrBlank()) {
            appendLine("System description: " + info.description.take(1000))
        }
    }.trimEnd()

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

    private fun readExitTraceBase64(info: ApplicationExitInfo): String? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return null
        return runCatching {
            val input = info.traceInputStream ?: return@runCatching null
            input.use { stream ->
                val output = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var total = 0
                while (total < MAX_TRACE_BYTES) {
                    val count = stream.read(buffer, 0, minOf(buffer.size, MAX_TRACE_BYTES - total))
                    if (count <= 0) break
                    output.write(buffer, 0, count)
                    total += count
                }
                val truncated = stream.read() != -1
                Base64.encodeToString(output.toByteArray(), Base64.NO_WRAP) +
                    if (truncated) "\\n[trace truncated]" else ""
            }
        }.getOrNull()
    }

    private fun writeCrashReport(context: Context, threadName: String, throwable: Throwable) {
        val directory = context.filesDir
        val stage = runCatching {
            File(directory, STAGE_NAME).takeIf { it.isFile }?.readText(Charsets.UTF_8)
        }.getOrNull()
        val versionName = runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull()
        val report = formatReport(
            threadName = threadName,
            throwable = throwable,
            lastCompletedStage = stage,
            versionName = versionName,
            processId = Process.myPid(),
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
        processId: Int? = null,
        packageName: String = "com.mangoloads.currentlens"
    ): String {
        val writer = StringWriter()
        throwable.printStackTrace(PrintWriter(writer))
        return buildString {
            appendLine("CurrentLens local startup crash report")
            appendLine("Timestamp: " + Instant.now().toString())
            appendLine("App: " + versionName.orEmpty())
            appendLine("Package: " + packageName)
            appendLine("Device: " + Build.MANUFACTURER + " " + Build.BRAND + " " + Build.MODEL)
            appendLine("Device code: " + Build.DEVICE)
            appendLine("Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")")
            appendLine("Build fingerprint: " + Build.FINGERPRINT)
            appendLine("Process id: " + (processId?.toString() ?: "unavailable"))
            appendLine("Thread: " + threadName)
            appendLine("Last completed startup stage: " + (lastCompletedStage ?: "unknown"))
            appendLine("Exception: " + throwable.javaClass.name)
            appendLine()
            append(writer.toString())
        }.take(MAX_REPORT_CHARS)
    }
}
