package com.mangoloads.currentlens

import android.app.Application
import android.content.Context

/**
 * Installs crash diagnostics before AndroidX Startup ContentProviders are created.
 *
 * This ordering matters because PrismalAGSL registers an AndroidX Startup initializer;
 * an exception in that initializer occurs before MainActivity.onCreate().
 */
class CurrentLensApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        LaunchCrashDiagnostics.install(base)
        LaunchCrashDiagnostics.recordStage(base, "Application.attachBaseContext completed")
    }

    override fun onCreate() {
        super.onCreate()
        LaunchCrashDiagnostics.recordStage(this, "Application.onCreate completed")
    }
}
