import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks {
    bootJar {
        enabled = false
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    mainClass.set("schedule.Application")
}

allprojects {
    group = "org.kwan.meeting"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.spring.io/release")
        maven("https://repo.spring.io/milestone/")
        maven("https://jitpack.io")
    }
}


java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "21"
    }
}


subprojects {
    val kotlinVersion: String by project
    val coroutinesVersion: String by project
    val springBootVersion: String by project
    val slf4jVersion: String by project
    val logbackVersion: String by project
    val logbackContribVersion: String by project
    val kotlinFakerVersion: String by project

    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    tasks {
        withType<Test> {
            useJUnitPlatform()
            maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2)
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "21"
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "21"
        }

        bootJar {
            enabled = false
        }

        jar {
            enabled = true
        }
    }

    allOpen {
        annotations(
            "jakarta.persistence.Entity"
        )
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-test")

        implementation("com.fasterxml.jackson.core:jackson-databind")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("jakarta.inject:jakarta.inject-api:2.0.1")

        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        implementation("ch.qos.logback:logback-core:$logbackVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("ch.qos.logback.contrib:logback-jackson:$logbackContribVersion")
        implementation("ch.qos.logback.contrib:logback-json-classic:$logbackContribVersion")

        implementation("io.github.serpro69:kotlin-faker:$kotlinFakerVersion")

        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
