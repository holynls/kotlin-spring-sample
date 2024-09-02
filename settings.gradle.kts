pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val jibPluginVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val querydslPluginVersion: String by settings

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.spring.io/release")
        maven("https://repo.spring.io/milestone/")
        maven("https://jitpack.io")
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.ewerk.gradle.plugins.querydsl") version querydslPluginVersion
        id("com.google.cloud.tools.jib") version jibPluginVersion
    }
}

rootProject.name = "schedule"

include(":domain")
include(":application")
include(":port")

project(":domain").projectDir = file("domain")
project(":application").projectDir = file("application")
project(":port").projectDir = file("port")
