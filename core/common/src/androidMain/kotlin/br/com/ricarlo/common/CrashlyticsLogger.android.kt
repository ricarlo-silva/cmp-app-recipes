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
            firebaseCrashlytics.setCustomKey(key, "$value")
        }
    }
}
