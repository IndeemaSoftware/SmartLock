//
//  AuthManager.swift
//  smartLock
//
//  Created by Oleksandr Verbych on 5/19/18.
//  Copyright © 2018 Oleksandr Verbych. All rights reserved.
//

import Foundation


protocol SmartLockManagerDelegate: class {
    func onLoginFinished()
    func onLoginFailed(error : String)
}

class SmartLockManager {
    static let shared = SmartLockManager()
    
    weak var delegate: SmartLockManagerDelegate?
    
    
    typealias JSONObject              = [String: Any]
    typealias JSONArray               = [ JSONObject ]
    typealias APIClientCompletion     = (_ data: JSONObject?, _ error: String?) -> Void
    typealias APIClientBoolCompletion = (_ success: Bool, _ data: JSONObject?, _ error: String?) -> Void
    
    
    
    let baseURL = "http://smartlock.indeema.com/open_door"
    
    let defaultSession = URLSession(configuration: .default)
    
    fileprivate func request(urlString: String, method: String, jsonObject: JSONObject? = nil) -> URLRequest? {
        guard let url = URL(string: urlString) else {
            print("Can not create request for URL:\(urlString)")
            return nil
        }
        
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = method
        
        urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        if let jsonObject = jsonObject {
            do {
                let jsonData = try JSONSerialization.data(withJSONObject: jsonObject, options: [])
                urlRequest.httpBody = jsonData
            } catch {
                print(error.localizedDescription)
                return nil
            }
        }
        return urlRequest
    }
    
    fileprivate func performDataTask(withRequest request: URLRequest, completion: @escaping APIClientBoolCompletion) {
        
        let dataTask = defaultSession.dataTask(with: request) { (data, response, error) in
            if let httpResponse = response as? HTTPURLResponse {
                if httpResponse.statusCode == 200,
                    let data = data,
                    let statusTuple = self.statusFromData(responceData: data) {
                    
                    if statusTuple.success {
                        completion(true, nil, nil)
                    } else {
                        completion(false, nil , "Error occured. Code: \(statusTuple.code)")
                    }
                } else {
                    completion(false , nil, "Error occured. Code: \(httpResponse.statusCode)")
                }
            }else {
                completion(false , nil, "Error occured. Wrong Response.")
            }
        }
        dataTask.resume()
    }
    
    fileprivate func validateResponse(_ response: HTTPURLResponse?) -> (valid: Bool, statusCode: Int) {
        guard let sStatusCode = response?.statusCode else {
            return (false, 0)
        }
        
        if sStatusCode >= 400 && sStatusCode <= 500 {
            print("Response is NOT OK! StatusCode: \(sStatusCode)")
            return (false, sStatusCode)
        }
        
        print("Response is OK! StatusCode: \(sStatusCode)")
        return (true, sStatusCode)
    }
    
    func statusFromData(responceData : Data) -> (success: Bool, code: Int)? {
        let json = try? JSONSerialization.jsonObject(with: responceData, options: [])
        
        guard let lDictionary = json as? [String: Any] else {
            return nil
        }
        
        guard let statusCode = lDictionary["code"] as? Int else {
            return nil
        }
            
        guard let status = lDictionary["status"] as? Bool  else {
            return nil
        }
        
        return (status, statusCode)
    }
    
    
    func login(login: String, password: String) {
        
        let url = NSURL(string: "https://redmine.indeema.com/users/current.json")
        let request = NSMutableURLRequest(url: url! as URL)
        
        let config = URLSessionConfiguration.default
        let userPasswordString = "\(login):\(password)"
        let userPasswordData = userPasswordString.data(using: String.Encoding.utf8)
        let base64EncodedCredential = userPasswordData!.base64EncodedString(options:NSData.Base64EncodingOptions(rawValue: 0))
        let authString = "Basic \(base64EncodedCredential)"
        config.httpAdditionalHeaders = ["Authorization" : authString]
        let session = URLSession(configuration: config)
        let task = session.dataTask(with: request as URLRequest) { (data, response, error) -> Void in
            print("Response \(String(describing: response)), error \(String(describing: error))")
            if let httpResponse = response as? HTTPURLResponse {
                if (httpResponse.statusCode == 200) {
                    if let lData = data {
                        if let lApiKey = self.apiKeyFromData(responceData: lData) {
                            self.saveApiKey(key: lApiKey)
                            self.saveUsername(username: login)
                            self.delegate?.onLoginFinished()
                            return
                        }
                    }
                } else  if (httpResponse.statusCode == 401) {
                    self.delegate?.onLoginFailed(error: "Wrong credentials")
                    return
                }
            }
            if ((error) != nil) {
                self.delegate?.onLoginFailed(error: (error?.localizedDescription)!)
            } else {
                self.delegate?.onLoginFailed(error: "Unexpected error")
            }
        }
        task.resume()
    }

    func saveApiKey(key: String) {
        KeyStorage.saveKey(key: key)
    }
    
    func saveUsername(username: String) {
        KeyStorage.saveUsername(username: username)
    }
    
    func apiKeyFromData(responceData : Data) -> String? {
        let json = try? JSONSerialization.jsonObject(with: responceData, options: [])
        
        if let lDictionary = json as? [String: Any] {
            if let lUserDictionary = lDictionary["user"] as? [String: Any] {
                if let lApiKey = lUserDictionary["api_key"] as? String {
                    return lApiKey;
                }
            }
        }
        return nil
    }
    
    func logOut() {
        KeyStorage.clearUserData()
    }
}

// MARK: - Open door handling
extension SmartLockManager {
    func openDoor(completion: @escaping APIClientBoolCompletion) {
        
        guard let apiKey = KeyStorage.readKey() else {
            fatalError("Mising APIKEY")
        }
        
        let infoJSON = ["api_key": apiKey]
    
        guard let urlRequest = request(urlString: baseURL, method: "POST", jsonObject: infoJSON) else {
            completion(false, nil, "bad request")
            return
        }
            
        performDataTask(withRequest: urlRequest, completion: completion)
    }
}
