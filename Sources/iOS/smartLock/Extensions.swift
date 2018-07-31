//
//  Extensions.swift
//  smartLock
//
//  Created by Vitaliy Yarkun on 5/20/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import Foundation
import MBProgressHUD

//MARK:- MBProgressHUD extension
extension MBProgressHUD {
    
    class func showProgressHud(view: UIView, animated: Bool) -> MBProgressHUD {
        let progressHUD = MBProgressHUD.showAdded(to:view, animated: true)
        progressHUD.mode = MBProgressHUDMode.indeterminate
        progressHUD.bezelView.color = UIColor.clear
        progressHUD.bezelView.style = .solidColor
        //progressHUD.bezelView.alpha = 0.8
        progressHUD.contentColor = UIColor.black
        progressHUD.removeFromSuperViewOnHide = true
        return progressHUD
    }
    
    class func showAnimated(onView view: UIView?) {
        if let view = view {
            let _ = MBProgressHUD.showProgressHud(view: view, animated: true)
        }
    }
    
    class func hideAnimated(forView view: UIView?) {
        if let view = view {
            MBProgressHUD.hide(for: view, animated: true)
        }
    }
}

//MARK:- UIColor extension
extension UIColor {
    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")
        
        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }
    
    convenience init(rgb: Int) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF
        )
    }
}
