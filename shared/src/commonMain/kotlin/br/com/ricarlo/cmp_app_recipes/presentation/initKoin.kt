package br.com.ricarlo.cmp_app_recipes.presentation

import br.com.ricarlo.common.di.commonModule
import br.com.ricarlo.home.di.homeModule
import br.com.ricarlo.login.di.loginModule
import br.com.ricarlo.network.networkModule
import br.com.ricarlo.notification.di.notificationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            commonModule,
            networkModule,
            loginModule,
            notificationModule,
            homeModule
        )
    }
}
