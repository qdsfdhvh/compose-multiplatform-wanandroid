import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose").version(Versions.composeJb)
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
        implementation(project(":common"))
        api(compose.runtime)
        api(compose.foundation)
        api(compose.animation)
        api(compose.material)
        api(compose.materialIconsExtended)
        api("androidx.paging:paging-common:${Versions.paging}")
      }
    }
    val androidMain by getting {
      dependencies {
        api("androidx.activity:activity:1.4.0")
        api("io.coil-kt:coil-compose:1.4.0")
        api("androidx.dynamicanimation:dynamicanimation-ktx:1.0.0-alpha03")
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
