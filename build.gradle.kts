import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

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
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31"
    kotlin("kapt") version "1.4.31"
    id("org.asciidoctor.jvm.convert") version "3.1.0"
}

apply(plugin = "com.google.cloud.tools.appengine")

group = "com.estn"

val majorVersion = 0
val minorVersion = 0
val patchVersion = 1
val gitCommitSha = getGitHash()

version = "$majorVersion.$minorVersion.$patchVersion.$gitCommitSha"

println("current version: $version")

java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val kotlin = "1.4.31"
val echarts = "4.9.0"
val fontAwesome = "5.12.0"
val momentJs = "2.24.0"
val jQuery = "3.5.1"

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
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
    // database
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("mysql:mysql-connector-java")
    // tools
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    // config
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    // frontend
    implementation("org.webjars:echarts:$echarts")
    implementation("org.webjars:momentjs:$momentJs")
    runtimeOnly("org.webjars:font-awesome:$fontAwesome")
    runtimeOnly("org.webjars:jquery:$jQuery")
    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

configure<com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension> {
    val version = project.version.toString()
            .replace('.', '-')
    deploy {
        projectId = "GCLOUD_CONFIG"
        setVersion(version)
    }
}

tasks {
    "asciidoctor"(org.asciidoctor.gradle.jvm.AsciidoctorTask::class) {
        options(mapOf("doctype" to "book"))
        sourceDir(file("./src/main/asciidoc"))
        attributes(mutableMapOf("snippets" to File("${project.buildDir.path}/snippets")))
        setOutputDir(file("${project.buildDir.path}/resources/main/static/api/dokumentace"))
        mustRunAfter("test")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.findByName("test")?.finalizedBy("asciidoctor")

fun getGitHash(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}