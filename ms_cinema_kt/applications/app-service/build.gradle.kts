val awsSecretsManagerVersion: String by project

apply(plugin = "org.springframework.boot")


dependencies {
    implementation(project(":reactive-web"))
    implementation(project(":r2dbc-postgresql"))
    implementation(project(":model"))
    implementation(project(":usecase"))
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.github.bancolombia:aws-secrets-manager-sync:$awsSecretsManagerVersion")
    implementation("software.amazon.awssdk:secretsmanager")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }
}

tasks.getByName<Jar>("jar") {
    // To disable the *-plain.jar
    enabled = false
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    // Sets output jar name
    archiveFileName = "${project.parent?.name}.${archiveExtension.get()}"
}