//
//  Created by Ricarlo Silva on 22/09/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import shared

class PerformanceProviderImpl {
    // private lazy var performance: FirebasePerformance = {
    //     FirebasePerformance.getInstance()
    // }()

    func trace<T>(_ name: String, action: () -> T) -> T {
    //     // if (!performance.isPerformanceCollectionEnabled) {
    //     //     return action()
    //     // }
    //     // return performance.newTrace(name).trace {
    //     //     action()
    //     // }
        return action()
    }

    func setPerformanceCollectionEnabled(enabled: Bool) {
        // performance.isPerformanceCollectionEnabled = enabled
    }
}
