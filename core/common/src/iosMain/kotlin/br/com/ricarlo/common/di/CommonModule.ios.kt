package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsLogger
import br.com.ricarlo.common.RemoteConfigProvider
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun Module.includeModule() = Unit

fun createIosModule(
    reporter: CrashlyticsLogger,
    remoteConfigProvider: RemoteConfigProvider
) = module {
    single<CrashlyticsLogger> { reporter }
    single<RemoteConfigProvider> { remoteConfigProvider }
}
