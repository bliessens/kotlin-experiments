val modules = listOf<String>("coroutines/chapter01")

tasks.create("generateModuleSkeletons") {

    require(modules.isNotEmpty())

    doLast {
        modules.forEach { projectName ->
            mkdir("$projectName/src/main/kotlin")
            mkdir("$projectName/src/main/resources")
            mkdir("$projectName/src/test/kotlin")
            mkdir("$projectName/src/test/resources")
            File("$projectName/build.gradle.kts").also { buildFile ->
                if (buildFile.createNewFile()) {
                    buildFile.appendText(
                        """
                        plugins {
                            id("com.melexis.java-conventions")
                        }
                         """.trimIndent()
                    )

                    val projectPath = projectName.replace("/", ":")
                    File("${rootProject.projectDir}/settings.gradle.kts").appendText("include(\":${projectPath}\")\n")

                }
            }
        }
    }
}
