//
//  MainViewController.swift
//  smartLock
//
//  Created by Oleksandr Verbych on 5/19/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import UIKit
import MBProgressHUD

class HomeViewController: UIViewController {

    @IBOutlet private weak var openSwitch: SmartLockSwitch!
    
    @IBOutlet private weak var openProgress: UIProgressView!
    @IBOutlet private weak var progressLabel: UILabel!
    
    fileprivate var timer: Timer?
    fileprivate var time = 0.0
    
    fileprivate let buttonText = "Toggle to open the door"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        openSwitch.shouldToggleByTapGesture = false
        hideOpennigProgress(true)
    }

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .default
    }
    
    // MARK: - Public methods
    func openDoorManually() {
        openSwitch.setOn(true, animated: true)
        handleOpeningTheDoor()
    }
    
    // MARK: - Private methods
    private func hideOpennigProgress(_ hidden: Bool) {
        openProgress.isHidden = hidden
        progressLabel.text = hidden ? "Toggle to open the door" : "\(timerDuration)"
        progressLabel.textColor = hidden ? UIColor(rgb: 0x2ec76d) : UIColor(rgb: 0xe83250)
    }
    
    @IBAction private func openDoorSwitchToggled(_ sender: Any) {
        handleOpeningTheDoor()
    }
    
    private func handleOpeningTheDoor() {
        openSwitch.isEnabled = false
        progressLabel.text = ""
        
        let sendOpenDoorRequest: () -> Void = { [weak self] in
            self?.openDoor { [weak self] (success, error) in
                guard let strongSelf = self else { return }
                if success {
                    DispatchQueue.main.async {
                        strongSelf.openProgress.setProgress(0.0, animated: false)
                        strongSelf.hideOpennigProgress(false)
                        strongSelf.enableTimer()
                    }
                    
                    DispatchQueue.main.asyncAfter(deadline: .now() + strongSelf.timerDuration, execute: {
                        strongSelf.openSwitch.isEnabled = true
                        strongSelf.openSwitch.setOn(false, animated: true)
                        strongSelf.hideOpennigProgress(true)
                    })
                } else {
                    DispatchQueue.main.async {
                        strongSelf.openSwitch.setOn(false, animated: true)
                        strongSelf.openSwitch.isEnabled = true
                        strongSelf.hideOpennigProgress(true)
                        strongSelf.showErrorAlert(errorText: error ?? "Something went wrong")
                    }
                }
            }
        }
        
        if KeyStorage.shouldCheckLocationBeforeOpening,
           LocationManager.instance.isNearIndeemaOffice == false {
            showOpeningDoorConfirmationDialog(cancelActionHandler: { [unowned self] in
                    self.openSwitch.setOn(false, animated: true)
                    self.openSwitch.isEnabled = true
                    self.hideOpennigProgress(true)
                }, confirmActionHandler: {
                    sendOpenDoorRequest()
                })
        } else {
            sendOpenDoorRequest()
        }
    }
    
    private func showErrorAlert(errorText: String) {
        let alert = UIAlertController(title: "Oops...", message: errorText , preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Got it", style: .default, handler: { _ in
        })
        alert.addAction(okAction)
        
        self.present(alert, animated: true, completion: nil)
    }
    
    private func openDoor(completion: @escaping (_ success: Bool, _ error: String?) -> Void) {
        MBProgressHUD.showAnimated(onView: self.view)
        SmartLockManager.shared.openDoor { (success, json, error) in
            DispatchQueue.main.async { [weak self] in
                MBProgressHUD.hideAnimated(forView: self?.view)
            }
            if success {
                completion(true, nil)
            } else {
                completion(false, error ?? "Something went wrong")
            }
        }
    }

    private let timerUpdateInterval = 0.2
    private let timerDuration = 2.0
    
    private func showOpeningDoorConfirmationDialog(cancelActionHandler: @escaping () -> Void,
                                                   confirmActionHandler: @escaping () -> Void) {
        let alertVC = UIAlertController(title: "Warning", message: "You are far away from the office. Are you sure that you want to open the door?", preferredStyle: .alert)
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel) { _ in
            cancelActionHandler()
        }
        let acceptAction = UIAlertAction(title: "Open", style: .default) { _ in
            confirmActionHandler()
        }
        alertVC.addAction(cancelAction)
        alertVC.addAction(acceptAction)
        present(alertVC, animated: true, completion: nil)
    }
}

// MARK:- Timer Animation
private extension HomeViewController {
    func enableTimer() {
        progressLabel.text = "\(timerDuration)"
        
        timer = Timer.scheduledTimer(timeInterval: timerUpdateInterval, target: self, selector: #selector(runTimedCode), userInfo: nil, repeats: true)
    }
    
    func disableTimer() {
        timer?.invalidate()
        timer = nil
        time = 0.0
    }
    
    @objc func runTimedCode() {
        let newTime = time + timerUpdateInterval
        if newTime > timerDuration {
            disableTimer()
        } else {
            time = newTime
            updateProgressBar(time)
        }
    }
    
    func updateProgressBar(_ secondsPassed: Double) {
        DispatchQueue.main.async { [weak self] in
            if let aSelf = self {
                let progress = Float(secondsPassed / aSelf.timerDuration)
                aSelf.openProgress.setProgress(progress, animated: true)
                
                let progressText = String(format: "%.1f", (aSelf.timerDuration - secondsPassed))
                aSelf.progressLabel.text = progressText
            }
        }
    }
}
