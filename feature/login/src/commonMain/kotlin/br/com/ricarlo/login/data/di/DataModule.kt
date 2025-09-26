package br.com.ricarlo.login.data.di

import br.com.ricarlo.login.data.repository.AuthRepositoryImpl
import br.com.ricarlo.login.domain.repository.AuthRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataModule = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}
