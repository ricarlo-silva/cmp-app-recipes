package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsLogger
import br.com.ricarlo.common.RemoteConfigProvider
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun Module.includeModule() = Unit

fun createIosModule(
    crashlytics: CrashlyticsLogger,
    remoteConfig: RemoteConfigProvider
) = module {
    single<CrashlyticsLogger> { crashlytics }
    single<RemoteConfigProvider> { remoteConfig }
}
