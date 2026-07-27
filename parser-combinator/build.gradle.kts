plugins {
    id("com.melexis.java-conventions")
    // id("com.google.devtools.ksp") version "1.9.0-1.0.11"
}

dependencies {
    testImplementation(libs.bundles.testing)

    implementation(libs.parsus)

//    implementation("io.arrow-kt:arrow-optics:1.2.0")
//    ksp("io.arrow-kt:arrow-optics-ksp-plugin:1.2.0")
}
