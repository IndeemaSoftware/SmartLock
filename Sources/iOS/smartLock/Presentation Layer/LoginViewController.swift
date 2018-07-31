//
//  ViewController.swift
//  smartLock
//
//  Created by Oleksandr Verbych on 5/19/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import UIKit
import MBProgressHUD

class LoginViewController: UIViewController, SmartLockManagerDelegate {

    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        usernameTextField.delegate = self
        passwordTextField.delegate = self
        addKeyboardDismissTapGesture()
    }
    
    @IBAction func onLoginBtnClick(sender: UIButton) {
        MBProgressHUD.showAnimated(onView: self.view)
        SmartLockManager.shared.delegate = self
        SmartLockManager.shared.login(login: usernameTextField.text!, password: passwordTextField.text!)
    }
    
    @IBAction func showPasswordButtonPressed(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected
        passwordTextField.isSecureTextEntry = !sender.isSelected
    }

    func onLoginFinished() {
        DispatchQueue.main.async {
            MBProgressHUD.hideAnimated(forView: self.view)
            let storyBoard = UIStoryboard(name: "Main", bundle:nil)
            guard let vc = storyBoard.instantiateViewController(withIdentifier: "HomeViewController") as? HomeViewController else { return }
            self.present(vc, animated: true, completion: nil)
        }
    }
    
    func onLoginFailed(error: String) {
        DispatchQueue.main.async {
            MBProgressHUD.hideAnimated(forView: self.view)
            let alert = UIAlertController(title: "Error", message: error, preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
    }
}

// MARK: - Dismiss Keyboard tap gesture
extension LoginViewController {
    fileprivate func addKeyboardDismissTapGesture() {
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(handleTapGesture(_:)))
        self.view.addGestureRecognizer(tapGestureRecognizer)
    }
    
    @objc func handleTapGesture(_ gesture: UITapGestureRecognizer) {
        dismissKeyboard()
    }
    
    fileprivate func dismissKeyboard() {
        self.view.endEditing(true)
    }
}

extension LoginViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == usernameTextField {
            passwordTextField.becomeFirstResponder()
            return true
        } else {
            textField.resignFirstResponder()
            onLoginBtnClick(sender: UIButton())
            return true
        } 
    }
}
