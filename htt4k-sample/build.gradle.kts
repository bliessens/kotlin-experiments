plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

    api(libs.http4k.core)
    api(libs.http4k.server.netty)
    api(libs.http4k.client.apache)
    runtimeOnly(libs.logback.classic)
}

description = "htt4k-sample"
