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
include(":core:domain")
include(":feature")
include(":core:model")
include(":core:common")
include(":core:component")
include(":core:data")
include(":core:datastore")
include(":core:database")
include(":core:sync")
