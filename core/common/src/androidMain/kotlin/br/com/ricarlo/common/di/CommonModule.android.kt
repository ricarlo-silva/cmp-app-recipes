package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsProvider
import br.com.ricarlo.common.CrashlyticsProviderImpl
import br.com.ricarlo.common.PerformanceProvider
import br.com.ricarlo.common.PerformanceProviderImpl
import br.com.ricarlo.common.RemoteConfigProvider
import br.com.ricarlo.common.RemoteConfigProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

internal actual fun Module.includeModule() {
    singleOf(::CrashlyticsProviderImpl) bind CrashlyticsProvider::class
    singleOf(::RemoteConfigProviderImpl) bind RemoteConfigProvider::class
    singleOf(::PerformanceProviderImpl) bind PerformanceProvider::class
}
