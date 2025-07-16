package br.com.ricarlo.common

interface CrashlyticsLogger {
    fun log(message: String)
    fun recordException(throwable: Throwable)
    fun setUserId(userId: String)
    fun setCustomKey(key: String, value: Any)
}
