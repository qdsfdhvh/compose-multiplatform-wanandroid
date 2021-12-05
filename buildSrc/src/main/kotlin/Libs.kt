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
}
