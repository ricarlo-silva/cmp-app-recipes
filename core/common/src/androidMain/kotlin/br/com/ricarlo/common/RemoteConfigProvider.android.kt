package br.com.ricarlo.common

import com.google.firebase.remoteconfig.CustomSignals
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

internal class RemoteConfigProviderImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    scope: CoroutineScope
) : RemoteConfigProvider {
    init {
        scope.launch {
            fetchAndActivate()
        }
    }

    override suspend fun fetchAndActivate() {
        remoteConfig.fetchAndActivate().await()
    }

    override fun getString(key: String): String = remoteConfig.getString(key)
    override fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)
    override fun getLong(key: String): Long = remoteConfig.getLong(key)
    override fun getDouble(key: String): Double = remoteConfig.getDouble(key)
    override fun getFloat(key: String): Float = remoteConfig.getDouble(key).toFloat()

    override suspend fun setCustomSignals(customSignals: Map<String, Any>) {
        remoteConfig.setCustomSignals(CustomSignals.Builder().apply {
            customSignals.forEach { (key, value) ->
                when (value) {
                    is String -> put(key, value)
                    is Long -> put(key, value)
                    is Double -> put(key, value)
                }
            }
        }.build()).await()
    }
}
