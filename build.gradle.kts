import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "cheatahh.jvm"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("lib/airportAgentSim.jar"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
    languageVersion = "1.7"
}