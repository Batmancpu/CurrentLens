package com.mangoloads.currentlens

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Installs local crash diagnostics before AndroidX Startup ContentProviders are created.
 *
 * Disk reads, process-exit inspection and checkpoint writes run on Dispatchers.IO so
 * diagnostic recovery cannot add synchronous file/binder work to the startup main thread.
 * The Activity waits for the best-effort inspection before choosing the recovery or app UI.
 */
class CurrentLensApplication : Application() {
    private val startupInspectionComplete = CompletableDeferred<Unit>()
    private val startupDiagnosticsReady = CompletableDeferred<Unit>()
    private val startupDiagnosticsScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // Install the handler immediately; defer all file and system-service work.
        runCatching { LaunchCrashDiagnostics.install(base) }
        startupDiagnosticsScope.launch {
            try {
                runCatching { LaunchCrashDiagnostics.beginStartup(base) }
            } finally {
                startupInspectionComplete.complete(Unit)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        startupDiagnosticsScope.launch {
            try {
                startupInspectionComplete.await()
                runCatching {
                    LaunchCrashDiagnostics.recordStage(
                        this@CurrentLensApplication,
                        "Application.onCreate completed"
                    )
                }
            } finally {
                startupDiagnosticsReady.complete(Unit)
            }
        }
    }

    suspend fun awaitStartupDiagnostics() {
        startupDiagnosticsReady.await()
    }
}
