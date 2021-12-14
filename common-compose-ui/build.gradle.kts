import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version Versions.composeJb
  id("com.android.library")
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
