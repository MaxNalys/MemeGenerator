plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.memegenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.memegenerator"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation (libs.androidx.core.ktx.v1120)
    implementation (libs.androidx.appcompat.v170)
    implementation (libs.material.v190)
    implementation (libs.androidx.constraintlayout)
    implementation (libs.androidx.activity.ktx.v172)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Retrofit and OkHttp
    implementation (libs.retrofit)
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.okhttp.v4100)
    implementation (libs.logging.interceptor.v4100)

    // Glide for image loading (if needed)
    implementation (libs.github.glide)
    annotationProcessor (libs.glide.compiler)

    // Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit.v121)
    androidTestImplementation (libs.androidx.espresso.core.v361)
}
