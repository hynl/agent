pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AgentRTW"
include(
    ":app",
    ":core:common",
    ":core:network",
//    ":core:database",
//    ":core:data",
//    ":core:domain",
//    ":feature:home"
)

include(":core:network")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":feature")
include(":core:model")
