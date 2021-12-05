object Libs {

  object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.lang}"
    const val coroutinesCore =
      "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}"
    const val coroutinesAndroid =
      "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
  }

  object Androidx {
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val paging = "androidx.paging:paging-common:${Versions.paging}"
  }

  object Square {
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
  }

  object Ktor {
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val clientOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
  }
}
