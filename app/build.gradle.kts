plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val stabilityTestBuild = providers.gradleProperty("stabilityTest").orNull == "true"

android {
    namespace = "com.mangoloads.currentlens"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.mangoloads.currentlens"
        minSdk = 26
        targetSdk = 35
        versionCode = 9
        versionName = "0.2.7"
        manifestPlaceholders["appLabel"] = if (stabilityTestBuild) "brrrNews Stability" else "brrrNews"
        buildConfigField("boolean", "STABILITY_TEST_BUILD", stabilityTestBuild.toString())
    }

    signingConfigs {
        if (stabilityTestBuild) {
            create("stability") {
                storeFile = rootProject.file(".ci/stability-debug.keystore")
                storePassword = "currentlens-stability-test-only"
                keyAlias = "currentlens-stability"
                keyPassword = "currentlens-stability-test-only"
            }
        }
    }

    buildTypes {
        getByName("debug") {
            if (stabilityTestBuild) {
                applicationIdSuffix = ".stability"
                versionNameSuffix = "-stability"
                signingConfig = signingConfigs.getByName("stability")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.06.01"))
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jsoup:jsoup:1.23.2")

    implementation("com.github.styropyr0:PrismalAGSL:v1.0.6")

    testImplementation("junit:junit:4.13.2")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
