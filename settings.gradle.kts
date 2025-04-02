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
    ":core:network",
)

include(":core:network")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":feature")
include(":core:model")
include(":core:common")
include(":core:component")
