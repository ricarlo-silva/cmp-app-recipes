package br.com.ricarlo.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorHttpClient {
    fun httpClient(
        plugins: List<HttpClientPlugin<Any, Any>> = emptyList(),
        tokenManager: TokenManager
    ) = HttpClient {
        defaultRequest {
            host = BuildConfig.BASE_URL
            url {
                protocol = URLProtocol.HTTPS
            }
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = BuildConfig.TIMEOUT.toLong()
            connectTimeoutMillis = BuildConfig.TIMEOUT.toLong()
            socketTimeoutMillis = BuildConfig.TIMEOUT.toLong()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(tokenManager.getToken(), tokenManager.getRefreshToken())
                }
                refreshTokens {
                    BearerTokens(tokenManager.getToken(), tokenManager.getRefreshToken())
                }
            }
        }

        val customHeaderPlugin = createClientPlugin("CustomHeaderPlugin") {
            onRequest { request, _ ->
                // TODO: Add custom headers to the request
                request.headers.append("X-Custom-Header", "Default value")
            }
        }
        install(customHeaderPlugin)

        plugins.forEach { plugin ->
            install(plugin)
        }
    }
}
