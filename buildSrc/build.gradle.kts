plugins {
    `kotlin-dsl`
}

val kotlin = "1.8.10"

dependencies {
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}")
}

repositories {
    gradlePluginPortal()
}
