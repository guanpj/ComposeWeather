plugins {
    kotlin("multiplatform").apply(false)
    kotlin("android").apply(false)
    kotlin("jvm").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}

buildscript {
    val sqlDelightVersion = "1.5.5"
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}

/*tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}*/
