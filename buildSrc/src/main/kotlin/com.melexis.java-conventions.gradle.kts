import com.melexis.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("idea")
    id("org.jlleitschuh.gradle.ktlint")
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
        languageVersion.set(JavaLanguageVersion.of(26))
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

ktlint {
    version.set("1.8.0")

    additionalEditorconfig.set(mapOf("max_line_length" to "120"))
}

// tasks.withType<KotlinCompile>() {
//    kotlinOptions {
//        freeCompilerArgs = listOf(
//            "-Xexplicit-api=strict", // force explicit visibility modifiers
//            "-Xjsr305=strict" // something with @Nullable annotations ?!
//        )
//    }
// }

tasks {
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        when (this.name) {
            "compileKotlin" -> {
                dependsOn(
                    tasks.named("ktlintMainSourceSetFormat"),
                    tasks.named("ktlintKotlinScriptFormat"),
                )
            }

            "compileTestKotlin" -> {
                dependsOn(tasks.named("ktlintTestSourceSetFormat"))
            }
        }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
