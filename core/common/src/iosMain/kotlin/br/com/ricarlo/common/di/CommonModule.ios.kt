package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsLogger
import br.com.ricarlo.common.CrashlyticsLoggerImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

internal actual fun Module.others() {
    singleOf(::CrashlyticsLoggerImpl) bind CrashlyticsLogger::class
}
