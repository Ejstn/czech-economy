import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.google.cloud.tools:appengine-gradle-plugin:2.2.0")
    }
}

repositories {
    mavenCentral()
    jcenter()
}

extra["springCloudVersion"] = "Hoxton.SR5"

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
    id("org.asciidoctor.jvm.convert") version "3.1.0"
}

apply(plugin = "com.google.cloud.tools.appengine")

group = "com.estn"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // starters
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // gcp
    implementation("org.springframework.cloud:spring-cloud-gcp-starter")
    implementation("org.springframework.cloud:spring-cloud-gcp-starter-sql-mysql")
    // reactive
    implementation("io.reactivex.rxjava2:rxjava")
    // json, xml
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    // database
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("mysql:mysql-connector-java")
    // tools
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    // config
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // frontend
    implementation("org.webjars:echarts:4.7.0")
    implementation("org.webjars:momentjs:2.24.0")
    runtimeOnly("org.webjars:font-awesome:5.12.0")
    runtimeOnly("org.webjars:jquery:3.5.1")
    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

configure<com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension> {
    deploy {
        projectId = "GCLOUD_CONFIG"
        version = "GCLOUD_CONFIG"
    }
}

tasks {
    "asciidoctor"(org.asciidoctor.gradle.jvm.AsciidoctorTask::class) {
        options(mapOf("doctype" to "book"))
        sourceDir(file("./src/main/asciidoc"))
        attributes(mutableMapOf("snippets" to File("${project.buildDir.path}/snippets")))
        setOutputDir(file("${project.buildDir.path}/resources/main/static/api/dokumentace"))
        mustRunAfter ("test")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.findByName("test")?.finalizedBy("asciidoctor")