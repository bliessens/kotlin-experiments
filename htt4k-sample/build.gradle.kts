plugins {
    id("com.melexis.java-conventions")
}

val http4k = "6.56.0.0"

dependencies {
    api("org.http4k:http4k-core:$http4k")
    api("org.http4k:http4k-server-netty:$http4k")
    api("org.http4k:http4k-client-apache:$http4k")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.5")
}

description = "htt4k-sample"
