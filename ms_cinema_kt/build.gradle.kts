import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion: String by project
val jacocoVersion: String by project
val awssdkVersion: String by project
val kotlinxCoroutinesVersion: String by project

plugins {
    id("co.com.bancolombia.cleanArchitecture") version "3.15.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.3" apply false
    id("org.sonarqube") version "4.4.1.3373" apply true
    id("jacoco") apply true
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

sonar {
    val modules = subprojects.map { subproject ->
        subproject.projectDir.toString().replace(project.projectDir.toString() + "/", "")
    }
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.modules", modules.joinToString(","))
        property(
            "sonar.sources",
            "src,deployment,settings.gradle.kts,build.gradle.kts,${
                modules.joinToString(",") { module ->
                    "${module}/build.gradle.kts"
                }
            }"
        )
        property("sonar.exclusions", "**/MainApplication.kt")
        property("sonar.test", "src/test")
        property("sonar.java.binaries", "")
        property("sonar.junit.reportsPath", "")
        property("sonar.java.coveragePlugin", "jacoco")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacocoMergedReport/jacocoMergedReport.xml"
        )
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "jacoco")
    apply(plugin = "io.spring.dependency-management")
    dependencies {
        implementation(platform("software.amazon.awssdk:bom:$awssdkVersion"))
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxCoroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$kotlinxCoroutinesVersion")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
    project.tasks.test.get().finalizedBy(project.tasks.jacocoTestReport)
    project.tasks.jacocoTestReport {
        dependsOn(project.tasks.test)
        reports {
            xml.required = true
            xml.outputLocation = layout.buildDirectory.file("reports/jacoco.xml")
            csv.required = false
            html.outputLocation = layout.buildDirectory.dir("reports/jacocoHtml")
        }
    }
}

jacoco {
    toolVersion = jacocoVersion
    reportsDirectory = layout.buildDirectory.dir("reports")
}

tasks.withType<JacocoReport> {
    dependsOn(subprojects.map { project -> project.tasks.jacocoTestReport })
    additionalSourceDirs.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    sourceDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    classDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().output }))
    executionData.setFrom(
        project.fileTree('.') {
            include("**/build/jacoco/test.exec")
        }
    )
    reports {
        xml.required = true
        csv.required = false
        html.required = true
    }
}