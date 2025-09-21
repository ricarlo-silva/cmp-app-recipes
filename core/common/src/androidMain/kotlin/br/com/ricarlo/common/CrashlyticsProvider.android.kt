package br.com.ricarlo.common

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class CrashlyticsProviderImpl(
    private val scope: CoroutineScope
) : CrashlyticsProvider {

    private val crashlytics: FirebaseCrashlytics by lazy {
        FirebaseCrashlytics.getInstance()
    }

    override fun log(message: String) {
        scope.launch {
            crashlytics.log(message)
        }
    }

    override fun recordException(throwable: Throwable) {
        scope.launch {
            crashlytics.recordException(throwable)
        }
    }

    override fun setUserId(userId: String) {
        scope.launch {
            crashlytics.setUserId(userId)
        }
    }

    override fun setCustomKey(key: String, value: Any) {
        scope.launch {
            when (value) {
                is Boolean -> crashlytics.setCustomKey(key, value)
                is Double -> crashlytics.setCustomKey(key, value)
                is Float -> crashlytics.setCustomKey(key, value)
                is Int -> crashlytics.setCustomKey(key, value)
                is Long -> crashlytics.setCustomKey(key, value)
                is String -> crashlytics.setCustomKey(key, value)
                else -> crashlytics.log("Error setting custom key $key with value $value")
            }
        }
    }
}
