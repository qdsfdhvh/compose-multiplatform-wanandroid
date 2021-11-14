import org.gradle.api.JavaVersion

object Versions {

  object Kotlin {
    const val lang = "1.5.31"
  }

  object Java {
    val lang = JavaVersion.VERSION_11
    const val jvmTarget = "11"
  }

  const val agp = "7.0.3"
  const val spotless = "5.17.0"
  const val ktLint = "0.42.1"

  const val compose = "1.0.0-beta6-dev455"
}
