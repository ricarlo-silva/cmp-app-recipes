package br.com.ricarlo.network.utils

import kotlinx.serialization.json.Json

val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}

inline fun <reified T> T.toJson(): String? = runCatching {
    json.encodeToString(this)
}.getOrNull()

inline fun <reified T> fromJson(json: String?): T? {
    if (json.isNullOrEmpty()) return null

    return Json.decodeFromString(json)
}
