plugins {
    kotlin("jvm").apply(false)
    id("org.jetbrains.compose").version("1.9.1").apply(false)
    kotlin("plugin.compose").version("2.2.21").apply(false)

    id("com.android.application").version("8.9.0").apply(false)
    id("com.android.library").version("8.9.0").apply(false)
    kotlin("android").version("2.2.21").apply(false)
    kotlin("multiplatform").version("2.2.21").apply(false)
}

buildscript {
    val sqlDelightVersion = "2.0.0"
    dependencies {
        classpath("app.cash.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}

/*tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}*/
