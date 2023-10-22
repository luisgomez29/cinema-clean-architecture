import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("co.com.bancolombia.cleanArchitecture") version "3.6.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springframework.boot") version "3.1.5" apply false
    id("org.sonarqube") version "4.2.1.3168" apply true
    id("jacoco") apply true
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
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
        jvmTarget = "17"
    }
}

sonarqube {
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
            "build/reports/jacoco/test/jacocoTestReport.xml"
        )
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "jacoco")
    apply(plugin = "io.spring.dependency-management")
    dependencies {
        implementation(platform("software.amazon.awssdk:bom:2.21.5"))
        implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.5"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.3")
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
    toolVersion = "0.8.10"
    reportsDirectory = layout.buildDirectory.dir("reports")
}

tasks.withType<JacocoReport> {
    dependsOn(subprojects.map { project -> project.tasks.jacocoTestReport })
    additionalSourceDirs.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    sourceDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    classDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().output }))
    executionData.setFrom(
        project.fileTree(project.buildDir) {
            include("**/build/jacoco/test.exec")
        }
    )
    reports {
        xml.required = true
        csv.required = false
        html.required = true
    }
}