plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("dev.icerock.mobile.multiplatform-resources") version Versions.generator
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
        api(Libs.Kotlin.stdlib)
        api(Libs.Kotlin.coroutinesCore)

        // MultiPlatform Resource https://github.com/icerockdev/moko-resources
        api("dev.icerock.moko:resources:${Versions.generator}")

        // Log https://github.com/AAkira/Napier
        api("io.github.aakira:napier:2.0.0")

        // Http https://github.com/square/okhttp
        api("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        api(Libs.Kotlin.coroutinesAndroid)
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

multiplatformResources {
  multiplatformResourcesPackage = Package.id
}
