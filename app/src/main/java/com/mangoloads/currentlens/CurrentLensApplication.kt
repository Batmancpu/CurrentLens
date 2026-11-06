package com.mangoloads.currentlens

import android.app.Application
import android.content.Context

/**
 * Installs local crash diagnostics before AndroidX Startup ContentProviders are created.
 *
 * Do not force a lower Prismal pipeline here. Production builds must retain Prismal's
 * automatic Liquid/Standard selection; the Stability build uses a plain first-screen
 * probe and lets the user opt into the complete Liquid Glass UI after that screen draws.
 */
class CurrentLensApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // Inspect the previous process before writing any stage belonging to this process.
        runCatching { LaunchCrashDiagnostics.install(base) }
        runCatching { LaunchCrashDiagnostics.beginStartup(base) }
    }

    override fun onCreate() {
        super.onCreate()
        LaunchCrashDiagnostics.recordStage(this, "Application.onCreate completed")
    }
}
