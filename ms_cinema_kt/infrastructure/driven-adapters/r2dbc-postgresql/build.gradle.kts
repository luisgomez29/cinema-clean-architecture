val mapstructVersion: String by project

plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":model"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
}
