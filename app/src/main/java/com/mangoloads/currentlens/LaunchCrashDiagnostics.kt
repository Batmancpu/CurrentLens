package com.mangoloads.currentlens

import android.content.Context
import android.os.Build
import android.os.Process
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

/** Stores a local crash report. Nothing is sent off-device. */
internal object LaunchCrashDiagnostics {
    private const val REPORT_NAME = "currentlens-last-crash.txt"
    private const val MAX_REPORT_CHARS = 30_000

    @Volatile
    private var installedHandler: Thread.UncaughtExceptionHandler? = null

    fun install(context: Context) {
        synchronized(this) {
            val previous = Thread.getDefaultUncaughtExceptionHandler()
            if (previous != null && previous === installedHandler) return
            val appContext = context.applicationContext
            val handler = Thread.UncaughtExceptionHandler { thread, throwable ->
                runCatching {
                    File(appContext.filesDir, REPORT_NAME)
                        .writeText(formatReport(thread.name, throwable), Charsets.UTF_8)
                }
                if (previous != null) previous.uncaughtException(thread, throwable)
                else {
                    Process.killProcess(Process.myPid())
                    exitProcess(10)
                }
            }
            installedHandler = handler
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }
    }

    fun read(context: Context): String? = runCatching {
        File(context.filesDir, REPORT_NAME).takeIf { it.isFile }
            ?.readText(Charsets.UTF_8)?.take(MAX_REPORT_CHARS)
    }.getOrNull()

    fun clear(context: Context): Boolean = runCatching {
        val file = File(context.filesDir, REPORT_NAME)
        !file.exists() || file.delete()
    }.getOrDefault(false)

    internal fun formatReport(threadName: String, throwable: Throwable): String {
        val writer = StringWriter()
        throwable.printStackTrace(PrintWriter(writer))
        return buildString {
            appendLine("CurrentLens local crash report")
            appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
            appendLine("Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
            appendLine("Thread: $threadName")
            appendLine("Exception: ${throwable.javaClass.name}")
            appendLine()
            append(writer.toString())
        }.take(MAX_REPORT_CHARS)
    }
}
