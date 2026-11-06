package com.mangoloads.currentlens

import android.app.Application
import android.content.Context
import android.os.Build
import com.styropyr0.prismal.PrismalGlass

/**
 * Installs crash diagnostics before AndroidX Startup ContentProviders are created.
 *
 * This ordering matters because PrismalAGSL registers an AndroidX Startup initializer;
 * an exception in that initializer occurs before MainActivity.onCreate().
 */
class CurrentLensApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // Disable the API 33+ AGSL renderer during launch stabilisation. Standard keeps
        // RenderEffect blur while avoiding the Liquid/AGSL shader path on affected GPUs.
        runCatching {
            StartupRenderPolicy.overrideFor(Build.VERSION.SDK_INT)?.let { tier ->
                PrismalGlass.pipelineOverride = tier
                LaunchCrashDiagnostics.recordStage(base, "Prismal pipeline forced to " + tier.name)
            }
        }
        runCatching { LaunchCrashDiagnostics.install(base) }
        runCatching { LaunchCrashDiagnostics.beginStartup(base) }
    }

    override fun onCreate() {
        super.onCreate()
        LaunchCrashDiagnostics.recordStage(this, "Application.onCreate completed")
    }
}
