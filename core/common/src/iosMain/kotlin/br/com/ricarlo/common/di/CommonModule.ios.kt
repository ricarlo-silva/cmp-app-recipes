package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsProvider
import br.com.ricarlo.common.RemoteConfigProvider
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun Module.includeModule() = Unit

fun createIosModule(
    crashlytics: CrashlyticsProvider,
    remoteConfig: RemoteConfigProvider
) = module {
    single<CrashlyticsProvider> { crashlytics }
    single<RemoteConfigProvider> { remoteConfig }
}
