package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsLogger
import br.com.ricarlo.common.CrashlyticsLoggerImpl
import br.com.ricarlo.common.RemoteConfigProvider
import br.com.ricarlo.common.RemoteConfigProviderImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind


internal actual fun Module.includeModule() {
    singleOf(::CrashlyticsLoggerImpl) bind CrashlyticsLogger::class
    single { FirebaseCrashlytics.getInstance() }
    singleOf(::RemoteConfigProviderImpl) bind RemoteConfigProvider::class
}
