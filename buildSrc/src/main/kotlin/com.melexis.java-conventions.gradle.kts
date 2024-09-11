import com.melexis.Versions

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("idea")
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.mockito}")
    testImplementation("org.assertj:assertj-core:${Versions.assertj}")
}

group = "com.melexis"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//tasks.withType<KotlinCompile>() {
//    kotlinOptions {
//        freeCompilerArgs = listOf(
//            "-Xexplicit-api=strict", // force explicit visibility modifiers
//            "-Xjsr305=strict" // something with @Nullable annotations ?!
//        )
//    }
//}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}