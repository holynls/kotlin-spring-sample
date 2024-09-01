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