import org.gradle.api.GradleException

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val stabilityTestBuild = providers.gradleProperty("stabilityTest").orNull == "true"
val requireStableSigning = providers.gradleProperty("requireStableSigning").orNull == "true"
val signingStoreFile = providers.environmentVariable("CURRENTLENS_SIGNING_KEYSTORE_PATH").orNull?.let { file(it) }
val signingStorePassword = providers.environmentVariable("CURRENTLENS_SIGNING_STORE_PASSWORD").orNull
val signingKeyAlias = providers.environmentVariable("CURRENTLENS_SIGNING_KEY_ALIAS").orNull
val signingKeyPassword = providers.environmentVariable("CURRENTLENS_SIGNING_KEY_PASSWORD").orNull

if (requireStableSigning) {
    val missing = buildList {
        if (signingStoreFile == null) add("CURRENTLENS_SIGNING_KEYSTORE_PATH")
        if (signingStorePassword.isNullOrBlank()) add("CURRENTLENS_SIGNING_STORE_PASSWORD")
        if (signingKeyAlias.isNullOrBlank()) add("CURRENTLENS_SIGNING_KEY_ALIAS")
        if (signingKeyPassword.isNullOrBlank()) add("CURRENTLENS_SIGNING_KEY_PASSWORD")
    }
    if (missing.isNotEmpty()) {
        throw GradleException("Stable APK signing is required; missing: ${missing.joinToString()}")
    }
    if (signingStoreFile?.isFile != true) {
        throw GradleException("Stable APK signing keystore does not exist at CURRENTLENS_SIGNING_KEYSTORE_PATH")
    }
}

android {
    namespace = "com.mangoloads.currentlens"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.mangoloads.currentlens"
        minSdk = 26
        targetSdk = 35
        versionCode = 10
        versionName = "0.2.8"
        manifestPlaceholders["appLabel"] = if (stabilityTestBuild) "brrrNews Stability" else "brrrNews"
        buildConfigField("boolean", "STABILITY_TEST_BUILD", stabilityTestBuild.toString())
    }

    signingConfigs {
        if (requireStableSigning) {
            create("stable") {
                storeFile = signingStoreFile!!
                storePassword = signingStorePassword!!
                keyAlias = signingKeyAlias!!
                keyPassword = signingKeyPassword!!
            }
        }
    }

    buildTypes {
        getByName("debug") {
            if (stabilityTestBuild) {
                applicationIdSuffix = ".stability"
                versionNameSuffix = "-stability"
            }
            if (requireStableSigning) {
                signingConfig = signingConfigs.getByName("stable")
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
