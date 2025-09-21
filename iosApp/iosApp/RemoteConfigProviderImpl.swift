//
// Created by Ricarlo Silva on 20/09/25.
// Copyright (c) 2025 orgName. All rights reserved.
//

import FirebaseRemoteConfig
import shared

internal class RemoteConfigProviderImpl: RemoteConfigProvider {

    private lazy var remoteConfig: RemoteConfig = {
        let remoteConfig = RemoteConfig.remoteConfig()
        let settings = RemoteConfigSettings()
        settings.minimumFetchInterval = 0
        remoteConfig.configSettings = settings
        let defaults = RemoteConfigKey.companion.defaultConfig() as? [String: NSObject]
        remoteConfig.setDefaults(defaults)
        remoteConfig.addOnConfigUpdateListener { configUpdate, error in
            guard let configUpdate, error == nil else {
                return print(
                    "Error listening for config updates: \(String(describing: error))"
                )
            }

            if !configUpdate.updatedKeys.isEmpty {
                self.remoteConfig.activate { changed, error in
                    guard error == nil else {
                        return print(
                            "Error activate config: \(String(describing: error))"
                        )
                    }
                }
            }
        }
        return remoteConfig
    }()

    func fetchAndActivate() async {
        try? await remoteConfig.fetchAndActivate()
    }

    func getString(key: String) -> String {
        return remoteConfig[key].stringValue
    }

    func getBoolean(key: String) -> Bool {
        return remoteConfig[key].boolValue
    }

    func getLong(key: String) -> Int64 {
        return remoteConfig[key].numberValue.int64Value
    }

    func getDouble(key: String) -> Double {
        return remoteConfig[key].numberValue.doubleValue
    }

    func getFloat(key: String) -> Float {
        return remoteConfig[key].numberValue.floatValue
    }

    func setCustomSignals(customSignals: [String: Any]) async {
        var custom: [String: CustomSignalValue?] = [:]

        for (key, value) in customSignals {
            switch value {
            case let str as String:
                custom[key] = .string(str)
            case let int as Int:
                custom[key] = .integer(int)
            case let dbl as Double:
                custom[key] = .double(dbl)
            default: continue
            }
        }

        try? await remoteConfig.setCustomSignals(custom)
    }
}
