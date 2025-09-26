package br.com.ricarlo.login.presentation.di

import br.com.ricarlo.login.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val presentationModule = module {
    viewModelOf(::LoginViewModel)
}
