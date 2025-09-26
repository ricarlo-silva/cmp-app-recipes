package com.ricarlo.recipes

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.io.IOException
import java.util.Properties

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.getLocalProperties(filename: String): Map<String, Any> {
    val propsFile = rootProject.file(filename).takeIf { it.exists() } ?: file(filename)
    val props = Properties()

    if (propsFile.exists()) {
        try {
            propsFile.inputStream().use { props.load(it) }
        } catch (e: IOException) {
            logger.warn("Could not load properties from $filename: ${e.message}")
            return emptyMap()
        }
    } else {
        logger.info("Properties file $filename not found. No secret properties will be loaded.")
    }

    return props.entries.associate { entry ->
        val key = entry.key.toString()
            .replace(".", "_")
            .replace("-", "_")
            .uppercase()

        val value = entry.value
            .toString()
            .replace("\"", "\\\"")

        key to value
    }
}

internal fun Any?.isBoolean() = this?.toString()?.toBooleanStrictOrNull() != null

internal fun Any?.isInt() = this?.toString()?.toIntOrNull() != null

internal fun Any?.isLong() = this?.toString()?.toLongOrNull() != null

internal fun Any?.isFloat() = this?.toString()?.toFloatOrNull() != null

internal fun Any?.isDouble() = this?.toString()?.toDoubleOrNull() != null
