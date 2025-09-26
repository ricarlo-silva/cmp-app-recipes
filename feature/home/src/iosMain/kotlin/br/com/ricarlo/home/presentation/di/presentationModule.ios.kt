package br.com.ricarlo.home.presentation.di

import br.com.ricarlo.home.presentation.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val presentationModule = module {
    singleOf(::HomeViewModel)
}
