package br.com.ricarlo.notification.di

import br.com.ricarlo.notification.core.FcmHandler
import br.com.ricarlo.notification.core.IFcmHandler
import br.com.ricarlo.notification.data.remote.ApiNotification
import br.com.ricarlo.notification.data.remote.IApiNotification
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationModule = module {
    singleOf(::FcmHandler) bind IFcmHandler::class
    singleOf(::ApiNotification) bind IApiNotification::class
}
