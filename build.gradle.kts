val modules = listOf<String>()
tasks.create("addModule") {

    require(modules.isNotEmpty())

    doLast {
        modules.forEach {
            mkdir("$it/src/main/kotlin")
            mkdir("$it/src/main/resources")
            mkdir("$it/src/test/kotlin")
            mkdir("$it/src/test/resources")
            File("$it/build.gradle.kts").appendText(
                """
                plugins {
                    id("com.melexis.java-conventions")
                }
            """.trimIndent()
            )

            val projectName = it.replace("/", ":")
            File("${rootProject.projectDir}/settings.gradle.kts").appendText("include(\":${projectName}\")\n")
        }

    }

}