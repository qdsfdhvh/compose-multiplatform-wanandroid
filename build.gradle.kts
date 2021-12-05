buildscript {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.lang}")
    classpath("com.android.tools.build:gradle:${Versions.agp}")
  }
}

plugins {
  id("com.diffplug.spotless") version Versions.spotless
}

group = "me.seiko.chat"
version = "1.0"

allprojects {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
  configSpotless()
}

subprojects {
  configKotlinOptions()
}

fun Project.configKotlinOptions() {
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = Versions.Java.jvmTarget
      allWarningsAsErrors = true
      freeCompilerArgs = listOf(
        "-Xopt-in=kotlin.RequiresOptIn",
      )
    }
  }
}

fun Project.configSpotless() {
  apply(plugin = "com.diffplug.spotless")

  val ktLintConfigData = mapOf(
    "indent_size" to "2",
    "continuation_indent_size" to "2"
  )

  spotless {
    kotlin {
      target("**/*.kt")
      targetExclude("$buildDir/**/*.kt", "bin/**/*.kt")
      ktlint(Versions.ktLint).userData(ktLintConfigData)
    }
    kotlinGradle {
      target("*.gradle.kts")
      targetExclude("**/build/**")
      ktlint(Versions.ktLint).userData(ktLintConfigData)
    }
    format("xml") {
      target("**/res/**/*.xml")
    }
  }
}
