import org.gradle.api.JavaVersion

object Versions {

  object Kotlin {
    const val lang = "1.5.31"
    const val coroutines = "1.5.2"
  }

  object Java {
    val lang = JavaVersion.VERSION_11
    const val jvmTarget = "11"
  }

  const val agp = "7.0.3"
  const val spotless = "5.17.0"
  const val ktLint = "0.42.1"
  const val generator = "0.17.2"

  const val activity = "1.4.0"
  const val annotation = "1.3.0"
  const val core = "1.7.0"
  const val paging = "3.1.0"

  const val composeJb = "1.0.0"
  const val okhttp = "4.9.3"
  const val koin = "3.1.4"
  const val coil = "2.0.0-alpha05"
  const val napier = "2.0.0"
}
