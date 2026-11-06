package com.mangoloads.currentlens

import android.content.Context
import android.os.Build
import android.os.Process
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
        // Diagnostics must never become a new launch blocker if app-private storage
        // is unavailable or the device is in an unusual early-boot state.
        runCatching {
            val directory = context.applicationContext?.filesDir ?: context.filesDir
            val previousStage = runCatching {
                File(directory, STAGE_NAME).takeIf { it.isFile }?.readText(Charsets.UTF_8)
            }.getOrNull()
            val reportFile = File(directory, REPORT_NAME)
            if (!reportFile.isFile && !previousStage.isNullOrBlank() &&
                !previousStage.contains("First Compose frames completed")
            ) {
                runCatching { writeInterruptedStartupReport(context, previousStage) }
            }
            recordStage(context, "Application.attachBaseContext completed")
        }
    }

    /** Record the last completed startup boundary for the next crash report. */
    fun recordStage(context: Context, stage: String) {
        if (stage.isBlank()) return
        runCatching {
            File((context.applicationContext ?: context).filesDir, STAGE_NAME)
                .writeText(Instant.now().toString() + " | " + stage.take(240), Charsets.UTF_8)
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
        packageName: String = "com.mangoloads.currentlens"
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
        appendLine("The previous process ended before confirming the first stable Compose frames.")
        appendLine("This can indicate a native/render-thread crash, abrupt process termination,")
        appendLine("or a force-stop during launch. It is not a Java/Kotlin stack trace.")
    }.take(MAX_REPORT_CHARS)

    private fun writeInterruptedStartupReport(context: Context, lastCompletedStage: String) {
        val versionName = runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull()
        writeReportFile(
            context.filesDir,
            formatInterruptedStartupReport(lastCompletedStage, versionName, Process.myPid(), context.packageName)
        )
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
