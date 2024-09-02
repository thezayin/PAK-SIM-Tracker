pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url =uri("https://jitpack.io")  }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenCentral()
        maven { url =uri("https://jitpack.io")  }
    }
}

rootProject.name = "Pak Sim Details"
include(":app")
include(":ads")
include(":analytics")
include(":core:common")
include(":core:di")
include(":core:framework")
include(":core:values")
include(":splash")
include(":setting")
include(":web")
include(":server:data")
include(":server:domain")
include(":server:presentation")
include(":home:presentation")
include(":home:data")
include(":home:domain")
include(":premium")
