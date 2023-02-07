plugins {
    id("com.melexis.java-conventions")
}

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.insert-koin:koin-core:3.3.2")
//    testImplementation("io.insert-koin:koin-test-junit5:3.3.2") {
//        exclude(group="org.jetbrains.kotlin",module="kotlin-test-junit")
//    }

    implementation("org.kodein.di:kodein-di:7.18.0")
}
