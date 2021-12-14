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
include(":compiler:route-processor")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
