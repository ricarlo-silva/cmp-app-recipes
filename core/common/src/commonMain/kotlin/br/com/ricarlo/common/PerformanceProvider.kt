package br.com.ricarlo.common

interface PerformanceProvider {
    fun <T> trace(name: String, action: () -> T) : T
    fun setPerformanceCollectionEnabled(enabled: Boolean)
}
