//
//  SettingsViewController.swift
//  smartLock
//
//  Created by Viktor Rudyk on 5/20/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import UIKit

class SettingsViewController: UIViewController {
    @IBOutlet private weak var usernameLabel: UILabel!
    @IBOutlet private weak var userImageView: UIImageView!
    @IBOutlet private weak var shouldCheckLocationSwitch: UISwitch!
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        usernameLabel.text = KeyStorage.getUsername()
        shouldCheckLocationSwitch.isOn = KeyStorage.shouldCheckLocationBeforeOpening
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        userImageView.layer.cornerRadius = userImageView.bounds.height / 2
        userImageView.clipsToBounds = true
    }

    // MARK: - Private methods
    @IBAction private func shouldCheckLocationValueChanged(_ sender: Any) {
        let newValue = shouldCheckLocationSwitch.isOn
        KeyStorage.shouldCheckLocationBeforeOpening = newValue
        if newValue {
            LocationManager.instance.startUpdatingLocation()
        } else {
            LocationManager.instance.stopUpdatingLocation()
        }
    }
    
    @IBAction private func logOutPressed(_ sender: UIButton) {
        KeyStorage.clearUserData()
        LocationManager.instance.stopUpdatingLocation()
        DispatchQueue.main.async {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let loginViewController = storyboard.instantiateViewController(withIdentifier: "LoginViewController")
            UIApplication.shared.delegate?.window??.rootViewController?.dismiss(animated: false, completion: nil)
            UIApplication.shared.keyWindow?.rootViewController = loginViewController
        }
    }
    
    @IBAction private func closePressed(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
}
