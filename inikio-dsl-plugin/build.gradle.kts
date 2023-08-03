plugins {
    id("com.melexis.java-conventions")
    id("com.google.devtools.ksp") version "1.8.20-1.0.11"
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

val inikioVersion="0.1"

dependencies {

    implementation("com.github.serras.inikio:inikio-core:$inikioVersion")
    ksp("com.github.serras.inikio:inikio-ksp:$inikioVersion")
}