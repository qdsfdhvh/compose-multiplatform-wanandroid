plugins {
  kotlin("multiplatform")
  id("com.android.library")
}

group = "me.seiko.chat"
version = "1.0"

kotlin {
  android()
  jvm("desktop") {
    compilations.all {
      kotlinOptions.jvmTarget = "11"
    }
  }
  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        api("androidx.appcompat:appcompat:1.3.1")
        api("androidx.core:core-ktx:1.7.0")
      }
    }
    val androidTest by getting {
      dependencies {
        implementation("junit:junit:4.13.2")
      }
    }
    val desktopMain by getting
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
