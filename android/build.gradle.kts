plugins {
  id("org.jetbrains.compose").version(Versions.composeJb)
  id("com.android.application")
  kotlin("android")
}

group = Package.group
version = Package.versionName

dependencies {
  implementation(project(":common"))
  implementation(project(":common-compose-ui"))
  implementation("androidx.core:core:1.7.0")
}

android {
  compileSdk = AndroidSdk.compileSdk
  defaultConfig {
    applicationId = "me.seiko.compose.android"
    minSdk = AndroidSdk.minSdk
    targetSdk = AndroidSdk.targetSdk
    versionCode = Package.versionCode
    versionName = Package.versionName
  }
  compileOptions {
    sourceCompatibility = Versions.Java.lang
    targetCompatibility = Versions.Java.lang
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
}
