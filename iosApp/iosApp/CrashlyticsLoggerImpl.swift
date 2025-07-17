//
//  CrashlyticsLoggerImpl.swift
//  iosApp
//
//  Created by Ricarlo Silva on 17/07/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import FirebaseCrashlytics
import shared

internal class CrashlyticsLoggerImpl : NSObject, CrashlyticsLogger {

    func log(message: String) {
        Crashlytics.crashlytics().log(message)
    }

    func recordException(throwable: KotlinThrowable) {
        Crashlytics.crashlytics().record(error: throwable.asError())
    }

    func setCustomKey(key: String, value: Any) {
        Crashlytics.crashlytics().setCustomValue(value, forKey: key)
    }

    func setUserId(userId: String) {
        Crashlytics.crashlytics().setUserID(userId)
    }
}
