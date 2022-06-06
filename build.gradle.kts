@file:Suppress("SpellCheckingInspection")

plugins {
    kotlin("jvm") version "1.7.0-RC2"
}

group = "cheatahh.jvm"
version = "1.1"
val production = true

// Repository Lookups
repositories {
    mavenCentral()
}

// Used Dependencies
dependencies {
    val libraryRoot = "lib"

    val airportAgentSim = files("$libraryRoot/airportAgentSim.zip")
    val airportAgentSimObjectsIntegration = files("$libraryRoot/airportAgentSimObjectsIntegration.zip")

    if(production) {
        compileOnly(airportAgentSim) // Precompiled jar, do not export
        compileOnly(airportAgentSimObjectsIntegration) // Precompiled jar, do not export
    } else {
        implementation(airportAgentSim) // Precompiled jar, do not export
        implementation(airportAgentSimObjectsIntegration) // Precompiled jar, do not export
    }

    testImplementation(airportAgentSim)
    testImplementation(airportAgentSimObjectsIntegration)
    testImplementation(kotlin("test"))
}

// Kotlin Compiler Setup
tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.7"
    }
    kotlinOptions.freeCompilerArgs += arrayOf("-Xlambdas=indy", "-Xcontext-receivers")
}

// Test Setup
tasks.test {
    useJUnitPlatform()
}

// Kotlin Test Compiler Setup
tasks.compileTestKotlin {
    kotlinOptions {
        languageVersion = "1.7"
    }
    kotlinOptions.freeCompilerArgs += arrayOf("-Xskip-prerelease-check")
}