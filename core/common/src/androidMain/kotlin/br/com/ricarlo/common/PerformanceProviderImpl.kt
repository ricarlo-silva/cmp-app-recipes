package br.com.ricarlo.common

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.trace

internal class PerformanceProviderImpl : PerformanceProvider {

    private val performance: FirebasePerformance by lazy {
        FirebasePerformance.getInstance()
    }

    override fun <T> trace(name: String, action: () -> T): T {
        if (!performance.isPerformanceCollectionEnabled) {
            return action()
        }

        return performance.newTrace(name).trace {
            action()
        }
    }

    override fun setPerformanceCollectionEnabled(enabled: Boolean) {
        performance.isPerformanceCollectionEnabled = enabled
    }
}
