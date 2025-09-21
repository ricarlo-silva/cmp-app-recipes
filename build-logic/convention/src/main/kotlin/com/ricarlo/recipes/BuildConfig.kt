package com.ricarlo.recipes

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val KEY_ENV = "flavor"
private const val GENERATED_DIR = "generated/source/buildConfig/kotlin"
private const val CLASS_NAME = "BuildConfig"

internal fun Project.configureBuildConfig(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val outputDir = layout.buildDirectory.dir(GENERATED_DIR).get().asFile

    val packageName = "${group}.${name}"
    val file = outputDir.resolve("$packageName/$CLASS_NAME.kt")
    val properties = setOf("secrets.properties", "gradle.properties", "module.properties")
        .flatMap { getLocalProperties(it).entries }
        .associate { it.key to it.value }
    val env = properties[KEY_ENV] ?: Env.UAT.name

    file.parentFile.mkdirs()
    file.setWritable(true)
    file.writeText(
        buildString {
            appendLine("package $packageName\n")
            appendLine("internal object $CLASS_NAME {")
            properties.filter { (key, _) ->
                !Env.values().any { otherEnv ->
                    key.startsWith(otherEnv.name, ignoreCase = true) && otherEnv.name != env
                }
            }.forEach {
                val key = it.key.removePrefix("${env}_")
                val value = when {
                    it.value.isBoolean() ||
                            it.value.isInt() ||
                            it.value.isLong() ||
                            it.value.isFloat() ||
                            it.value.isDouble() -> it.value

                    else -> "\"${it.value}\""
                }
                appendLine("    const val $key = $value")
            }
            appendLine("    const val isUAT = ${env == Env.UAT.name}")
            appendLine("}")
        }
    )
    file.setReadOnly()

    sourceSets.named("commonMain") {
        kotlin.srcDir("build/$GENERATED_DIR")
    }
}

enum class Env {
    PRD,
    UAT
}
