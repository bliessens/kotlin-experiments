plugins {
    `kotlin-dsl`
}

val kotlin = "1.8.20"

dependencies {
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}")
}

repositories {
    gradlePluginPortal()
}
