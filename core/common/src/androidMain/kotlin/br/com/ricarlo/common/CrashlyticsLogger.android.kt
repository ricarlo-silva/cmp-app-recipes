package br.com.ricarlo.common

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class CrashlyticsLoggerImpl(
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val scope: CoroutineScope
): CrashlyticsLogger {
    override fun log(message: String) {
        scope.launch {
            firebaseCrashlytics.log(message)
        }
    }

    override fun recordException(throwable: Throwable) {
        scope.launch {
            firebaseCrashlytics.recordException(throwable)
        }
    }

    override fun setUserId(userId: String) {
        scope.launch {
            firebaseCrashlytics.setUserId(userId)
        }
    }

    override fun setCustomKey(key: String, value: Any) {
        scope.launch {
            when (value) {
                is Boolean -> firebaseCrashlytics.setCustomKey(key, value)
                is Double -> firebaseCrashlytics.setCustomKey(key, value)
                is Float -> firebaseCrashlytics.setCustomKey(key, value)
                is Int -> firebaseCrashlytics.setCustomKey(key, value)
                is Long -> firebaseCrashlytics.setCustomKey(key, value)
                is String -> firebaseCrashlytics.setCustomKey(key, value)
                else -> firebaseCrashlytics.setCustomKey(key, value.toString())
            }
        }
    }
}
