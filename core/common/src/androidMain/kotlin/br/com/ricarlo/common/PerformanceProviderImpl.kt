package br.com.ricarlo.common

import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.ktx.performance

internal class PerformanceProviderImpl : PerformanceProvider {

    private val performance: FirebasePerformance by lazy {
        Firebase.performance
    }

    override fun trace(name: String, action: () -> Unit) {
        val trace = performance.newTrace(name)
        trace.start()
        action()
        trace.stop()
    }
}
