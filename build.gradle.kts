@file:Suppress("SpellCheckingInspection")

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "cheatahh.jvm"
version = "0.1"

// Repository Lookups
repositories {
    mavenCentral()
}

// Used Dependencies
dependencies {
    compileOnly(files("lib/airportAgentSim.jar")) // Precompiled jar, do not export
    testImplementation(kotlin("test")) // JUnit 5.8.2
}

// Kotlin Compiler Setup
tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.6"
    }
    kotlinOptions.freeCompilerArgs += "-Xlambdas=indy"
}

// Test Setup
tasks.test {
    useJUnitPlatform()
}