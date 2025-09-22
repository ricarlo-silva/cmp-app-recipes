package br.com.ricarlo.common

interface PerformanceProvider {
    fun trace(name: String, action: () -> Unit)
}
