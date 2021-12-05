plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version Versions.Kotlin.lang
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
        api(Libs.Androidx.annotation)
        api(Libs.Square.okhttp)
        api(Libs.Square.okhttpLoggingInterceptor)
        api(Libs.Ktor.clientCore)
        api(Libs.Ktor.clientOkhttp)
        api(Libs.Ktor.clientJson)
        api(Libs.Ktor.clientSerialization)

        // MultiPlatform Resource https://github.com/icerockdev/moko-resources
        api("dev.icerock.moko:resources:${Versions.generator}")

        // Log https://github.com/AAkira/Napier
        api("io.github.aakira:napier:${Versions.napier}")

        // Di https://github.com/InsertKoinIO/koin
        api("io.insert-koin:koin-core:${Versions.koin}")
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
        api(Libs.Androidx.activity)
        api(Libs.Androidx.core)
        api("io.insert-koin:koin-android:${Versions.koin}")
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
