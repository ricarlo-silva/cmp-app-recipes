package br.com.ricarlo.common

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.CustomSignals
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val FETCH_INTERVAL = 3600L

internal class RemoteConfigProviderImpl(
    private val crashlytics: CrashlyticsProvider,
    private val scope: CoroutineScope
) : RemoteConfigProvider {

    private val remoteConfig: FirebaseRemoteConfig by lazy {
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
                    crashlytics.log("Error listening for config updates: ${p0.message}")
                }
            })
        }
    }

    init {
        fetchAndActivate()
    }

    override fun fetchAndActivate() {
        scope.launch {
            remoteConfig.fetchAndActivate().await()
        }
    }

    override fun getString(key: String): String = remoteConfig.getString(key)
    override fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)
    override fun getLong(key: String): Long = remoteConfig.getLong(key)
    override fun getDouble(key: String): Double = remoteConfig.getDouble(key)
    override fun getFloat(key: String): Float = remoteConfig.getDouble(key).toFloat()

    override fun setCustomSignals(customSignals: Map<String, Any>) {
        scope.launch {
            remoteConfig.setCustomSignals(CustomSignals.Builder().apply {
                customSignals.forEach { (key, value) ->
                    when (value) {
                        is String -> put(key, value)
                        is Long -> put(key, value)
                        is Double -> put(key, value)
                        is Int -> put(key, value.toLong())
                        is Float -> put(key, value.toDouble())
                        else -> crashlytics.recordException(
                            IllegalArgumentException("Unsupported type: ${value.javaClass.name} setting custom key $key with value $value")
                        )
                    }
                }
            }.build()).await()
        }
    }
}
