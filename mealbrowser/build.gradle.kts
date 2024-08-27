import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0-Beta")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                implementation("io.ktor:ktor-client-core:1.6.2")
                implementation("io.ktor:ktor-client-cio:1.6.2")
                implementation("io.ktor:ktor-client-json:1.6.2")
                implementation("io.ktor:ktor-client-serialization-jvm:1.6.2")
                implementation("io.ktor:ktor-client-serialization:1.6.2")
                implementation("io.coil-kt:coil-compose:2.4.0")
                implementation("com.arkivanov.decompose:decompose:2.0.0-beta-01")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-beta-01")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose-desktop-starter"
            packageVersion = "1.0.0"
        }
    }
}
