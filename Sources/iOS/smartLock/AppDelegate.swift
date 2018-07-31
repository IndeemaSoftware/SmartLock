//
//  AppDelegate.swift
//  smartLock
//
//  Created by Oleksandr Verbych on 5/19/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import UIKit
import IQKeyboardManager

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        if KeyStorage.shouldCheckLocationBeforeOpening {
            LocationManager.instance.startUpdatingLocation()
        }
        
        IQKeyboardManager.shared().isEnabled = true
        IQKeyboardManager.shared().toolbarDoneBarButtonItemText = "Hide"
        
        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = createInitialViewController()
        self.window?.makeKeyAndVisible()
        
        return true
    }
    
    fileprivate func createInitialViewController() -> UIViewController {
        var initialVCIdentifier = String()
        
        if KeyStorage.apiKeyExists,
            KeyStorage.usernameExists {
            initialVCIdentifier = "HomeViewController"
        } else {
            initialVCIdentifier = "LoginViewController"
        }
        
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let initialViewController = storyboard.instantiateViewController(withIdentifier: initialVCIdentifier)
        
        return initialViewController
    }

    func application(_ application: UIApplication,
                     performActionFor shortcutItem: UIApplicationShortcutItem,
                     completionHandler: @escaping (Bool) -> Void) {
        let isActionRecognized = handleShortcutAction(with: shortcutItem)
        completionHandler(isActionRecognized)
    }
    
    private func handleShortcutAction(with item: UIApplicationShortcutItem) -> Bool {
        guard let shortcutIdentifier = AppShortcutType(rawValue: item.type) else { return false }
        
        switch shortcutIdentifier {
        case .openDoor:
            guard let homeVC = window?.rootViewController as? HomeViewController else { return false }
            let openDoorAction = { [weak homeVC] in
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.1, execute: {
                    homeVC?.openDoorManually()
                })
            }
            
            if homeVC.presentedViewController != nil {
                homeVC.dismiss(animated: false) {
                    openDoorAction()
                }
            } else {
                openDoorAction()
            }
        }
        return true
    }
}

