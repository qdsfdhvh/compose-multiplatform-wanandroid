import org.gradle.api.JavaVersion

object Versions {

  object Kotlin {
    const val lang = "1.6.10"
    const val coroutines = "1.6.0-RC3"
  }

  object Java {
    val lang = JavaVersion.VERSION_17
    const val jvmTarget = "17"
  }

  const val agp = "7.0.3"
  const val spotless = "6.0.4"
  const val ktLint = "0.43.2"
  const val generator = "0.17.3"

  const val activity = "1.4.0"
  const val annotation = "1.3.0"
  const val core = "1.7.0"
  const val paging = "3.1.0"

  const val composeJb = "1.0.1-rc2"
  const val okhttp = "4.9.3"
  const val koin = "3.1.4"
  const val coil = "2.0.0-alpha05"
  const val napier = "2.0.0"
  const val ktor = "1.6.7"

  const val javaFx = "0.0.10"
}
