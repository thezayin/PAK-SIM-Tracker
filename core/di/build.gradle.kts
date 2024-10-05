plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization.json)
}

android {
    namespace = "com.thezayin.di"
    compileSdk =  libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":core:framework"))
    implementation(project(":analytics"))
    implementation(project(":splash"))
    implementation(project(":ads"))

    implementation(project(":web"))
    implementation(project(":setting"))
    implementation(project(":premium"))

    implementation(project(":home:data"))
    implementation(project(":home:domain"))
    implementation(project(":home:presentation"))

    implementation(project(":server:data"))
    implementation(project(":server:domain"))
    implementation(project(":server:presentation"))

    implementation(project(":recent:data"))
    implementation(project(":recent:domain"))
    implementation(project(":recent:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.measurement.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //json
    implementation(libs.kotlinx.serialization.json)

    //koin dependency injection
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    testImplementation(libs.koin.test.junit4)
    implementation(libs.koin.androidx.navigation)
}