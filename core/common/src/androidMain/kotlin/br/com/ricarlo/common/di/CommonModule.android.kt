package br.com.ricarlo.common.di

import br.com.ricarlo.common.BuildConfig
import br.com.ricarlo.common.CrashlyticsLogger
import br.com.ricarlo.common.CrashlyticsLoggerImpl
import br.com.ricarlo.common.RemoteConfigKey
import br.com.ricarlo.common.RemoteConfigProvider
import br.com.ricarlo.common.RemoteConfigProviderImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

private const val FETCH_INTERVAL = 3600L

internal actual fun Module.includeModule() {
    singleOf(::CrashlyticsLoggerImpl) bind CrashlyticsLogger::class
    single { FirebaseCrashlytics.getInstance() }

    single<FirebaseRemoteConfig> {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = if (BuildConfig.isUAT) 0 else FETCH_INTERVAL
            })

            setDefaultsAsync(RemoteConfigKey.defaultConfig())

            addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    if (configUpdate.updatedKeys.isNotEmpty()) {
                        activate()
                    }
                }

                override fun onError(p0: FirebaseRemoteConfigException) {
                    print("Error listening for config updates: ${p0.message}")
                }
            })
        }
    }
    singleOf(::RemoteConfigProviderImpl) bind RemoteConfigProvider::class
}
