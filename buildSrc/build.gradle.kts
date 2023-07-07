plugins {
    `kotlin-dsl`
}

val kotlin = "1.9.0"

dependencies {
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
}

repositories {
    gradlePluginPortal()
}
