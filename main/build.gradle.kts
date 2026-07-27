plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

    api(libs.xstream)
    api(libs.jackson.databind)
    api(libs.jackson.module.kotlin)
}

description = "main"
