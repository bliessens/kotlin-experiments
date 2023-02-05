import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin")
    id("maven-publish")
    id("idea")
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
//    api("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

group = "com.melexis"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<Test>() {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
//    kotlinOptions {
//        freeCompilerArgs = listOf("-Xexplicit-api=strict")
//    }
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}