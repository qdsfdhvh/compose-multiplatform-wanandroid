import org.gradle.api.JavaVersion

object Versions {

  object Kotlin {
    const val lang = "1.5.21"
  }

  object Java {
    val lang = JavaVersion.VERSION_11
    const val jvmTarget = "11"
  }

  const val agp = "7.0.2"
  const val spotless = "5.17.0"
  const val ktLint = "0.42.1"
}
