//
//  Created by Ricarlo Silva on 22/09/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import FirebasePerformance
import shared

internal class PerformanceProviderImpl: PerformanceProvider {

    private lazy var performance: Performance = {
        Performance.sharedInstance()
    }()

    func trace(name: String, action: @escaping () -> Any?) -> Any? {
        if !performance.isDataCollectionEnabled {
            return action()
        }
        guard let trace = Performance.startTrace(name: name) else {
            return action()
        }
        let result = action()
        trace.stop()
        return result
    }

    func setPerformanceCollectionEnabled(enabled: Bool) {
        performance.isDataCollectionEnabled = enabled
    }
}
