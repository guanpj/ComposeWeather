@file:Suppress("OPT_IN_IS_NOT_ENABLED")

import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.8.20"
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
}

//@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")
    //jvm()

    /*js(IR) {
        browser()
    }

    wasm {
        browser()
    }*/

    /*js(IR) {
        moduleName = "Compose_Weather_Wasm"
        browser()
        binaries.executable()
    }
    wasm {
        moduleName = "Compose_Weather_Wasm"
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    open = mapOf(
                        "app" to mapOf(
                            "name" to "google chrome",
                        )
                    ),
                )
            }
        }
        binaries.executable()
    }*/

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }

        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    val ktorVersion = "2.3.0"
    val sqlDelightVersion = "1.5.5"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")

                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("com.blankj:utilcodex:1.31.1")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization-jvm:${ktorVersion}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                //implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            }
        }

        /*val jsWasmMain by creating {
            dependsOn(commonMain)
        }

        val jsMain by getting {
            dependsOn(jsWasmMain)
            dependencies {
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-wasm:1.5.1-wasm0")
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-wasm:1.6.4-wasm0")
                //implementation("io.ktor:ktor-client-core-wasm:2.3.1-wasm0")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                //implementation("com.squareup.sqldelight:web-worker-driver:$sqlDelightVersion")
                //implementation("app.cash.sqldelight:web-worker-driver:$sqlDelightVersion")
                implementation("app.cash.sqldelight:web-worker-driver:2.0.0-rc02")
            }
        }

        val wasmMain by getting {
            dependsOn(jsWasmMain)
        }*/
    }
}

android {
    namespace = "com.me.guanpj.composeweather"

    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.me.guanpj.composeweather.db"
        sourceFolders = listOf("kotlin")
    }
}

compose.experimental {
    web.application {}
}

compose {
    val composeVersion = project.property("compose.version") as String
    kotlinCompilerPlugin.set(composeVersion)
    val kotlinVersion = project.property("kotlin.version") as String
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=$kotlinVersion")
}
