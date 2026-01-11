import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
//    compileOnly(libs.lint.api)
//    testImplementation(libs.lint.checks)
//    testImplementation(libs.lint.tests)

    compileOnly(libs.detekt.api)
    testImplementation(libs.detekt.test)

    testImplementation(kotlin("test"))
}
