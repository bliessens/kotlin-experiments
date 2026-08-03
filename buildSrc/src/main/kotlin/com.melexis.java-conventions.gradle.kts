import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("idea")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "com.melexis"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

ktlint {
    version.set("1.8.0")

    additionalEditorconfig.set(mapOf("max_line_length" to "120"))
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

/*
    withType<KotlinCompile> {
        when (this.name) {
            "compileKotlin" -> {
                dependsOn(
                    tasks.ktlintMainSourceSetFormat,
                    tasks.ktlintKotlinScriptFormat,
                )
            }

            "compileTestKotlin" -> {
                dependsOn(tasks.ktlintTestSourceSetFormat)
            }
        }
    }
*/
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
