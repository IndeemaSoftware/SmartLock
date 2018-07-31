//
//  LocationManager.swift
//  smartLock
//
//  Created by Maksym Husar on 6/26/18.
//  Copyright © 2018 Indeema. All rights reserved.
//

import Foundation
import CoreLocation

class LocationManager: NSObject {

    static let instance = LocationManager()
    
    var isNearIndeemaOffice: Bool? {
        guard let currentCoordinate = locationManager.location?.coordinate else { return nil }
        return IndeemaOfficeCircularRegion().contains(currentCoordinate)
    }
    
    private let locationManager: CLLocationManager
    
    private override init() {
        locationManager = CLLocationManager()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()
        super.init()
    }
    
    func startUpdatingLocation() {
        locationManager.startUpdatingLocation()
    }
    
    func stopUpdatingLocation() {
        locationManager.stopUpdatingLocation()
    }
}
