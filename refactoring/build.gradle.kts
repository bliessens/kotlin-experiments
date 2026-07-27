plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

    implementation(libs.httpclient)
    implementation(libs.spring.core)
    implementation(libs.spring.boot.actuator) {
        isTransitive = false
    }
    implementation(libs.slf4j.api)
}
