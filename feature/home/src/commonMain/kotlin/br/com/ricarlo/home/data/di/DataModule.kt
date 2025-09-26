package br.com.ricarlo.home.data.di

import br.com.ricarlo.home.data.remote.ApiHome
import br.com.ricarlo.home.data.remote.ApiHomeImpl
import br.com.ricarlo.home.data.repository.HomeRepositoryImpl
import br.com.ricarlo.home.domain.repository.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataModule = module {
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
    singleOf(::ApiHomeImpl) bind ApiHome::class
}
