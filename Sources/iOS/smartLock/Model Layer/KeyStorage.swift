//
//  KeyStorage.swift
//  smartLock
//
//  Created by Oleksandr Verbych on 5/19/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import Foundation
import Security
import KeychainSwift

var APIKEY: String? = nil

class KeyStorage {
    private static let apiKey = "apiKey"
    private static let usernameKey = "userNameKey"
    private static let shouldCheckLocationKey = "shouldCheckLocationKey"
    
    static var apiKeyExists: Bool {
        get {
            if let _ = KeyStorage.readKey() {
                return true
            } else {
                return false
            }
        }
    }
    
    static var usernameExists: Bool {
        get {
            if let _ = getUsername() {
                return true
            } else {
                return false
            }
        }
    }
    
    static func saveKey(key : String) {
        let keychain = KeychainSwift()
        keychain.set(key, forKey: apiKey)
    }
    
    static func readKey () -> String? {
        let keychain = KeychainSwift()
        return keychain.get(apiKey)
    }
    
    static func saveUsername(username : String) {
        let keychain = KeychainSwift()
        keychain.set(username, forKey: usernameKey)
    }
    
    static func getUsername () -> String? {
        let keychain = KeychainSwift()
        return keychain.get(usernameKey)
    }
    
    static func clearUserData() {
        let keychain = KeychainSwift()
        keychain.clear()
        UserDefaults.standard.removePersistentDomain(forName: Bundle.main.bundleIdentifier ?? "")
    }
    
    static var shouldCheckLocationBeforeOpening: Bool {
        get {
            return UserDefaults.standard.bool(forKey: shouldCheckLocationKey)
        }
        set {
            UserDefaults.standard.set(newValue, forKey: shouldCheckLocationKey)
        }
    }
}
