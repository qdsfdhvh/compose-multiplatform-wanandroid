plugins {
  id("org.jetbrains.compose").version(Versions.compose)
  id("com.android.application")
  kotlin("android")
}

group = "me.seiko.compose"
version = "1.0"

dependencies {
  implementation(project(":common"))
  implementation(project(":common-compose-ui"))
  implementation("androidx.activity:activity-compose:1.4.0")
}

android {
  compileSdk = AndroidSdk.compileSdk
  defaultConfig {
    applicationId = "me.seiko.compose.android"
    minSdk = AndroidSdk.minSdk
    targetSdk = AndroidSdk.targetSdk
    versionCode = 1
    versionName = "1.0"
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
