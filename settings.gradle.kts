dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "kotlin-experiments"

include(":htt4k-sample")
include(":main")
include(":coroutines:chapter01")
include(":dicontainer")
include(":java-interop")
include(":klogger")
include(":pricing-dsl")
include(":currying")
include(":refactoring")
include(":monoid")
include(":parser-combinator")
include(":result-monad")
