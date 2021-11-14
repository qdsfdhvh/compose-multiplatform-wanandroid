import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose").version(Versions.compose)
  id("com.android.library")
}

group = "me.seiko.chat.common.compose.ui"
version = "1.0"

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
        implementation(project(":common"))
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material)
        api(compose.animation)
      }
    }
    val androidMain by getting {
      dependencies {
        api("androidx.lifecycle:lifecycle-runtime:2.4.0")
        api("androidx.savedstate:savedstate:1.1.0")
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
