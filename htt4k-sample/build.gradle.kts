plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    api("org.http4k:http4k-core:4.36.0.0")
    api("org.http4k:http4k-server-netty:4.36.0.0")
    api("org.http4k:http4k-client-apache:4.36.0.0")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.5")
}

description = "htt4k-sample"
