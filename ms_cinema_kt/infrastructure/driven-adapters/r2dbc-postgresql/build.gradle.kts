plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":model"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//    implementation("org.mapstruct:mapstruct:1.5.5.Final")
//    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
//
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
//    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.1")
//    testImplementation("org.assertj:assertj-core:3.11.1")
//}

