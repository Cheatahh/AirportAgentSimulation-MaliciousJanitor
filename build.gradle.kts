@file:Suppress("SpellCheckingInspection")

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "cheatahh.jvm"
version = "0.3"

// Repository Lookups
repositories {
    mavenCentral()
}

// Used Dependencies
dependencies {
    val airportAgentSim = files("lib/airportAgentSim.zip")

    compileOnly(airportAgentSim) // Precompiled jar, do not export

    testImplementation(airportAgentSim)
    testImplementation(kotlin("test")) // JUnit 5.8.2
}

// Kotlin Compiler Setup
tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.6"
    }
    kotlinOptions.freeCompilerArgs += arrayOf("-Xlambdas=indy")
}

// Test Setup
tasks.test {
    useJUnitPlatform()
}