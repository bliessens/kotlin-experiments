plugins {
    `kotlin-dsl`
}

val kotlin = "2.0.0"

dependencies {
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
}

repositories {
    gradlePluginPortal()
}
