plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.gms.google.services)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.thalesnishida.todo"
    compileSdk {
        version = release(36)
    }

    packaging {
        resources {
            pickFirsts += "META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/androidx/room/room-compiler-processing/LICENSE.txt"
        }
    }

    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
        resolutionStrategy.force("org.jetbrains:annotations:23.0.0")
    }

    defaultConfig {
        applicationId = "com.thalesnishida.todo"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.hilt.compiler)
    implementation(libs.hilt.android.compiler)
    implementation(libs.androidx.appcompat)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.compiler)
    implementation(libs.android.work)
    implementation(libs.android.work.ktx)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.apache.commons)

    implementation(libs.hilt.work)
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.navigation)

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    kapt(libs.room.compiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation("androidx.compose.material3:material3-window-size-class:1.3.0")

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
