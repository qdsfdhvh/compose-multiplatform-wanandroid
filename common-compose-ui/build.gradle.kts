import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version Versions.composeJb
  id("com.android.library")
  id("com.google.devtools.ksp") version Versions.ksp
}

group = Package.group
version = Package.versionName

kotlin {
  android()
  jvm("desktop") {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.common)
        api(compose.runtime)
        api(compose.foundation)
        api(compose.animation)
        api(compose.material)
        api(compose.materialIconsExtended)
        api(Libs.Androidx.paging)

        implementation(projects.compiler.routeProcessor)
        ksp(projects.compiler.routeProcessor)
      }
    }
    val androidMain by getting {
      dependencies {
        api("io.coil-kt:coil-compose:${Versions.coil}")
      }
    }
    val desktopMain by getting {
      dependencies {
        api(compose.preview)
      }
    }
    val desktopTest by getting
  }
}

android {
  compileSdk = AndroidSdk.compileSdk
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
    minSdk = AndroidSdk.minSdk
    targetSdk = AndroidSdk.targetSdk
  }
  compileOptions {
    sourceCompatibility = Versions.Java.lang
    targetCompatibility = Versions.Java.lang
  }
}

fun KotlinDependencyHandler.ksp(dependencyNotation: DelegatingProjectDependency) {
  configurations["ksp"].dependencies.add(dependencyNotation)
}
