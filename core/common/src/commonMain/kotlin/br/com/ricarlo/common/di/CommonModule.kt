package br.com.ricarlo.common.di

import br.com.ricarlo.common.DeepLinkHandler
import br.com.ricarlo.common.IDeepLinkHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::DeepLinkHandler) bind IDeepLinkHandler::class
    factory<CoroutineScope> { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    others()
}

internal expect fun Module.others()
