package br.com.ricarlo.home.di

import br.com.ricarlo.home.data.di.dataModule
import br.com.ricarlo.home.domain.di.domainModule
import br.com.ricarlo.home.presentation.di.presentationModule
import org.koin.dsl.module

val homeModule = module {
    includes(dataModule, domainModule, presentationModule)
}
