package br.com.ricarlo.network

import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        KtorHttpClient.httpClient(
            plugins = getAll(),
            tokenManager = get()
        )
    }
    singleOf(::TokenManagerImpl) bind TokenManager::class
}
