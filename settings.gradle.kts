pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}
rootProject.name = "compose-multiplatform-study"

include(":android")
include(":desktop")
include(":common")
include(":common-compose-ui")
