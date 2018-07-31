//
//  IndeemaOfficeCircularRegion.swift
//  smartLock
//
//  Created by Maksym Husar on 6/26/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import Foundation
import CoreLocation

class IndeemaOfficeCircularRegion: CLCircularRegion {
    override init() {
        let center = CLLocationCoordinate2D(latitude: 49.823788550915395, longitude: 24.05266519017823)
        super.init(center: center, radius: 30, identifier: "IndeemaOffice")
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
