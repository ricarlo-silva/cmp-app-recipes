package br.com.ricarlo.login.presentation.di

import br.com.ricarlo.login.presentation.LoginViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val presentationModule = module {
    singleOf(::LoginViewModel)
}
