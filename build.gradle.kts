

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        classpath ("com.android.tools.build:gradle:4.1.3")
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }

}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id ("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}