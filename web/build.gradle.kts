import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.2.21"
    id("org.jetbrains.compose")
    kotlin("plugin.compose") version "2.2.21"
}

val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
    from(project(":shared").file("src/commonMain/composeResources"))
    into("build/processedResources/js/main")
}

afterEvaluate {
    project.tasks.findByName("jsProcessResources")?.finalizedBy(copyJsResources)
    project.tasks.findByName("jsProductionExecutableCompileSync")?.dependsOn(copyJsResources)
    project.tasks.findByName("jsDevelopmentExecutableCompileSync")?.dependsOn(copyJsResources)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "web.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
    }
}

compose.experimental {
    web.application {}
}
