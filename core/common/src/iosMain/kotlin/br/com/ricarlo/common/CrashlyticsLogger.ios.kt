package br.com.ricarlo.common

internal class CrashlyticsLoggerImpl: CrashlyticsLogger {
    override fun log(message: String) {
//        FIRCrashlytics.crashlytics().log(message)
    }

    override fun recordException(throwable: Throwable) {
//        CrashlyticsHelper
    }

    override fun setUserId(userId: String) {
//        FIRCrashlytics.crashlytics().setUserId(userId)
    }

    override fun setCustomKey(key: String, value: Any) {
//        FIRCrashlytics.crashlytics().setCustomKey(key, value)
    }
}
