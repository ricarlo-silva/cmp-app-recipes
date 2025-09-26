plugins {
    `kotlin-dsl`
}

group = "br.com.ricarlo.build_logic.convention"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.kotlinAndroid.toDep())
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

gradlePlugin {
    plugins {
        register("mavenPublish") {
            id = "recipes.convention.publish"
            implementationClass = "MavenPublishConventionPlugin"
        }
        register("multiplatform") {
            id = "recipes.convention.multiplatform"
            implementationClass = "MultiplatformPlugin"
        }
        register("androidLibrary") {
            id = "recipes.convention.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("codeAnalysis") {
            id = "recipes.convention.analysis"
            implementationClass = "AnalysisConventionPlugin"
        }
    }
}
