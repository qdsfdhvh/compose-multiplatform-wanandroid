plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:${Versions.ksp}")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}
