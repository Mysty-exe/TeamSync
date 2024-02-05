import org.gradle.api.tasks.Delete

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "7.1.2" apply false
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()

        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("io.realm:realm-gradle-plugin:10.17.0")
    }
}
