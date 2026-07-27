plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

    runtimeOnly(kotlin("script-runtime"))
}
