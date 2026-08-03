plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    testImplementation(libs.bundles.testing)

//    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.koin.core)
//    testImplementation("io.insert-koin:koin-test-junit5:3.3.2") {
//        exclude(group="org.jetbrains.kotlin",module="kotlin-test-junit")
//    }

    implementation(libs.kodein.di)
}
