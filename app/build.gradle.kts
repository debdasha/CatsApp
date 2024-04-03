plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.debdasha.catsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.debdasha.catsapp"
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
    lint {
        abortOnError = false
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.rxjava)
    annotationProcessor(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.rxjava2)
    implementation(libs.rxandroid)
    implementation(libs.glide)
    implementation(libs.glide.recyclerview)
    annotationProcessor(libs.glide.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.adapter)
    implementation(libs.easypermissions)
    implementation(libs.rxbinding)
}