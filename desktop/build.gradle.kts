import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version Versions.composeJb
  id("org.openjfx.javafxplugin") version Versions.javaFx
}

group = Package.group
version = Package.versionName

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
    withJava()
    // @see https://openjfx.io/openjfx-docs/#gradle
    javafx {
      version = "15"
      modules = listOf("javafx.controls", "javafx.swing", "javafx.web")
    }
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(project(":common"))
        implementation(project(":common-compose-ui"))
        implementation(compose.desktop.currentOs)
      }
    }
    val jvmTest by getting
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = Package.name
      packageVersion = Package.versionName
    }
  }
}
