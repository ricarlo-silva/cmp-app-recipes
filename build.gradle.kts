
import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.composeMultiplatform).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.google.services).apply(false)
    alias(libs.plugins.firebase.crashlytics).apply(false)
    alias(libs.plugins.firebase.perf).apply(false)
    alias(libs.plugins.modulegraph).apply(true)
    alias(libs.plugins.kotlin.parcelize).apply(false)
    alias(libs.plugins.spotless).apply(true)
    alias(libs.plugins.detekt).apply(true)
    alias(libs.plugins.kotlin.jvm).apply(false)
//    alias(libs.plugins.recipes.convention.analysis).apply(true)
}

moduleGraphConfig {
    heading.set("## Module Graph")
    setStyleByModuleType.set(true)
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.spotless.get().pluginId)
    }

    configure<SpotlessExtension> {
        // Configuration for Kotlin files
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt") // Exclude files in the build directory
            ktlint(libs.versions.ktlint.get())
                .setEditorConfigPath(rootProject.file(".editorconfig").path)
            toggleOffOn() // Allow toggling Spotless off and on within code files using comments
            trimTrailingWhitespace()
            ktfmt(libs.versions.ktfmt.get()).kotlinlangStyle()
        }

        // Additional configuration for Kotlin Gradle scripts
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(libs.versions.ktlint.get())
        }
    }

}
