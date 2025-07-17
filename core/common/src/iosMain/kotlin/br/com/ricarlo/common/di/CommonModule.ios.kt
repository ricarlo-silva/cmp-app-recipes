package br.com.ricarlo.common.di

import br.com.ricarlo.common.CrashlyticsLogger
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun Module.others() {

}

fun createIosModuleWithReporter(reporter: CrashlyticsLogger) = module {
    single<CrashlyticsLogger> { reporter }
}
