//
//  KoinHelpers.swift
//  iosApp
//
//  Created by Ricarlo Silva on 23/05/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import shared

class SwiftKClass<T>: NSObject, KotlinKClass {
    func isInstance(value: Any?) -> Bool { value is T }
    var qualifiedName: String? { String(reflecting: T.self) }
    var simpleName: String? { String(describing: T.self) }
}

func KClass<T>(for type: T.Type) -> KotlinKClass {
    SwiftType(type: type, swiftClazz: SwiftKClass<T>()).getClazz()
}

extension Koin_coreScope {
    func get<T>() -> T {
        // swiftlint:disable force_cast
        get(clazz: KClass(for: T.self), qualifier: nil, parameters: nil) as! T
        // swiftlint:enable force_cast
    }
}

func inject<T>(
    qualifier: Koin_coreQualifier? = nil,
    parameters: (() -> Koin_coreParametersHolder)? = nil
) -> T {
    // swiftlint:disable force_cast
    KoinGetKt.koinGet(clazz: KClass(for: T.self), qualifier: qualifier, parameters: parameters) as! T
    // swiftlint:enable force_cast
}

func injectLazy<T>(
    qualifier: Koin_coreQualifier? = nil,
    parameters: (() -> Koin_coreParametersHolder)? = nil
) -> () -> T {
    return {
        // swiftlint:disable force_cast
        KoinGetKt.koinGet(clazz: KClass(for: T.self), qualifier: qualifier, parameters: parameters) as! T
        // swiftlint:enable force_cast
    }
}
