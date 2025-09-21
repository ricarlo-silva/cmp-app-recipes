package br.com.ricarlo.common

interface RemoteConfigProvider {
    suspend fun fetchAndActivate()
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getLong(key: String): Long
    fun getDouble(key: String): Double
    fun getFloat(key: String): Float
    suspend fun setCustomSignals(customSignals: Map<String, Any>)
}
