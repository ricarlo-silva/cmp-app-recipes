package br.com.ricarlo.home.presentation.di

import br.com.ricarlo.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val presentationModule = module {
    viewModelOf(::HomeViewModel)
}
