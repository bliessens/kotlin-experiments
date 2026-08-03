plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
}
