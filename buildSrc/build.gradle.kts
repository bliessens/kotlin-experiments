plugins {
    `kotlin-dsl`
}

val kotlin = "2.0.0"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:14.2.0")
}

repositories {
    gradlePluginPortal()
}
