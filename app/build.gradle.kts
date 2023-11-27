
plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")

    id ("com.google.gms.google-services")

}

android {
    namespace = "com.presidentserviceconsult.dimaz"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.presidentserviceconsult.dimaz"
        minSdk = 21
        targetSdk = 32
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("com.facebook.android:facebook-android-sdk:16.0.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("com.appsflyer:af-android-sdk:6.12.4")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.onesignal:OneSignal:[4.0.0, 4.99.99]")
    implementation ("com.google.firebase:firebase-config-ktx:21.4.1")
    implementation ("androidx.room:room-ktx:2.5.0-alpha02")
    kapt ("androidx.room:room-compiler:2.5.0-alpha02")
    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

}