import com.google.cloud.tools.jib.gradle.JibExtension

plugins {
    id("com.google.cloud.tools.jib")
}

dependencies {
    val springDocMvcVersion: String by project

    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocMvcVersion")
}

tasks {
    configure<JibExtension> {
        from {
            image = "azul/zulu-openjdk-alpine:21.0.4-jdk"
        }

        to {
            image = "data-rize/schedule"
            tags = setOf("latest")
        }

        container {
            jvmFlags =
                listOf(
                    "-server",
                    "-Xms2g",
                    "-Xmx2g",
                    "-XX:+ParallelRefProcEnabled",
                    "-XX:-ResizePLAB",
                    "-XX:+UseG1GC",
                    "-Dcom.sun.management.jmxremote",
                    "-Dcom.sun.management.jmxremote.port=19080",
                    "-Dcom.sun.management.jmxremote.authenticate=false",
                    "-Dcom.sun.management.jmxremote.ssl=false ",
                )

            ports = listOf("8080")
        }
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
