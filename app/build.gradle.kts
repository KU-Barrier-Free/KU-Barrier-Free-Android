import org.gradle.kotlin.dsl.implementation
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

val localProperties = Properties().apply {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    } else {
        throw IllegalStateException("local.properties file not found")
    }
}

val mapsApiKey: String? = localProperties.getProperty("GOOGLE_MAPS_API_KEY")

android {
    namespace = "com.ganalijigi.kubf"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ganalijigi.kubf"
        minSdk = 30
        targetSdk = 35
        versionCode = 4
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = mapsApiKey.toString()
        buildConfigField("String", "GOOGLE_MAPS_ID", localProperties["GOOGLE_MAPS_ID"].toString())
        buildConfigField("String", "BASE_URL", localProperties["BASE_URL"].toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.okhttp.dnsoverhttps)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.core)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.manager)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Google Map
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)

    // Immutable
    implementation(libs.kotlinx.collections.immutable)

    // Network
    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
}
