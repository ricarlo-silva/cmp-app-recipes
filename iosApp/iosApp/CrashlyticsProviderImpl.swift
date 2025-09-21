//
//  CrashlyticsLoggerImpl.swift
//  iosApp
//
//  Created by Ricarlo Silva on 17/07/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import FirebaseCrashlytics
import shared

internal class CrashlyticsProviderImpl: NSObject, CrashlyticsLogger {

    private lazy var crashlytics: Crashlytics = {
        Crashlytics.crashlytics()
    }()

    func log(message: String) {
        #if DEBUG
            print(message)
        #else
            crashlytics.log(message)
        #endif
    }

    func recordException(throwable: KotlinThrowable) {
        crashlytics.record(error: throwable.asError())
    }

    func setCustomKey(key: String, value: Any) {
        crashlytics.setCustomValue(value, forKey: key)
    }

    func setUserId(userId: String) {
        crashlytics.setUserID(userId)
    }
}
