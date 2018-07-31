//
//  SmartLockSwitch.swift
//  IndeemaSwitch
//
//  Created by Maksym Husar on 6/25/18.
//  Copyright © 2018 Indeema. All rights reserved.
//

import UIKit

public class SmartLockSwitch: UIControl {
    public struct Configurations {
        var bgStateViewSideOffset: CGFloat = 12
        var fullChangeAnimationDuration: Double = 0.5
        var panDeltaMovementInPercentages: Double = 0.3
        
        var bgOnStateImage = #imageLiteral(resourceName: "switch-on")
        var bgOffStateImage = #imageLiteral(resourceName: "switch-off")
        
        var thumbOnStateImage = #imageLiteral(resourceName: "switch_circle-on")
        var thumbOffStateImage = #imageLiteral(resourceName: "switch_circle-off")
    }
    
    public private(set) var isOn: Bool = false
    public var shouldToggleByTapGesture: Bool = true

    public var config = Configurations() {
        didSet {
            subviews.forEach { $0.removeFromSuperview() }
            setupUI()
        }
    }
    
    private weak var thumbOnStateImageView: UIImageView?
    private let thumbView = UIView()
    private var leftThumbViewAnchor: NSLayoutConstraint?
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupUI()
    }
    
    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupUI()
    }
    
    // MARK: - Public methods
    
    /// does not send action
    public func setOn(_ on: Bool, animated: Bool) {
        self.isOn = on
        self.updateCurrentThumbPosition(animated: animated)
    }
    
    // MARK: - Private methods
    private func setupUI() {
        setupThumbView()
        setupBackgrouondStateViews()
        
        bringSubview(toFront: thumbView)

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.toggleSwitchState(_:)))
        tapGesture.cancelsTouchesInView = false
        self.addGestureRecognizer(tapGesture)
        
        let panGesture = UIPanGestureRecognizer(target: self, action: #selector(panRecognized(_:)))
        panGesture.cancelsTouchesInView = false
        thumbView.addGestureRecognizer(panGesture)
        
        updateCurrentThumbPosition(animated: false)
    }
    
    private func setupThumbView() {
        thumbView.backgroundColor = .clear
        thumbView.clipsToBounds = true
        thumbView.layer.cornerRadius = thumbView.bounds.height / 2.0
        addSubview(thumbView)
        thumbView.translatesAutoresizingMaskIntoConstraints = false
        
        thumbView.topAnchor.constraint(equalTo: self.topAnchor).isActive = true
        leftThumbViewAnchor = thumbView.leftAnchor.constraint(equalTo: self.leftAnchor)
        leftThumbViewAnchor?.isActive = true
        thumbView.heightAnchor.constraint(equalTo: self.heightAnchor).isActive = true
        thumbView.widthAnchor.constraint(equalTo: thumbView.heightAnchor).isActive = true
        thumbView.widthAnchor.constraint(equalTo: thumbView.heightAnchor).isActive = true
        
        let offStateImageView = UIImageView(image: config.thumbOffStateImage)
        offStateImageView.contentMode = .scaleAspectFit
        thumbView.addSubview(offStateImageView)
        offStateImageView.translatesAutoresizingMaskIntoConstraints = false
        
        offStateImageView.topAnchor.constraint(equalTo: thumbView.topAnchor).isActive = true
        offStateImageView.bottomAnchor.constraint(equalTo: thumbView.bottomAnchor).isActive = true
        offStateImageView.rightAnchor.constraint(equalTo: thumbView.rightAnchor).isActive = true
        offStateImageView.leftAnchor.constraint(equalTo: thumbView.leftAnchor).isActive = true
        
        let onStateImageView = UIImageView(image: config.thumbOnStateImage)
        onStateImageView.contentMode = .scaleAspectFit
        thumbView.addSubview(onStateImageView)
        onStateImageView.translatesAutoresizingMaskIntoConstraints = false
        
        onStateImageView.topAnchor.constraint(equalTo: thumbView.topAnchor).isActive = true
        onStateImageView.bottomAnchor.constraint(equalTo: thumbView.bottomAnchor).isActive = true
        onStateImageView.rightAnchor.constraint(equalTo: thumbView.rightAnchor).isActive = true
        onStateImageView.leftAnchor.constraint(equalTo: thumbView.leftAnchor).isActive = true
        
        thumbOnStateImageView = onStateImageView
    }
    
    private func setupBackgrouondStateViews() {
        let bgOnStateImageView = UIImageView(image: config.bgOnStateImage)
        bgOnStateImageView.contentMode = .scaleAspectFit
        addSubview(bgOnStateImageView)
        bgOnStateImageView.translatesAutoresizingMaskIntoConstraints = false
        
        bgOnStateImageView.topAnchor.constraint(equalTo: self.topAnchor).isActive = true
        bgOnStateImageView.bottomAnchor.constraint(equalTo: self.bottomAnchor).isActive = true
        bgOnStateImageView.leftAnchor.constraint(equalTo: self.leftAnchor, constant: config.bgStateViewSideOffset).isActive = true
        bgOnStateImageView.rightAnchor.constraint(equalTo: self.rightAnchor, constant: -config.bgStateViewSideOffset).isActive = true
        
        let bgOffStateContainerView = UIView()
        bgOffStateContainerView.backgroundColor = .clear
        bgOffStateContainerView.clipsToBounds = true
        addSubview(bgOffStateContainerView)
        bgOffStateContainerView.translatesAutoresizingMaskIntoConstraints = false
        
        bgOffStateContainerView.topAnchor.constraint(equalTo: self.topAnchor).isActive = true
        bgOffStateContainerView.bottomAnchor.constraint(equalTo: self.bottomAnchor).isActive = true
        bgOffStateContainerView.rightAnchor.constraint(equalTo: self.rightAnchor, constant: -config.bgStateViewSideOffset).isActive = true
        bgOffStateContainerView.leftAnchor.constraint(equalTo: thumbView.rightAnchor,
                                                      constant: -bounds.height * 0.5).isActive = true
        
        let bgOffStateImageView = UIImageView(image: config.bgOffStateImage)
        bgOffStateImageView.contentMode = .scaleAspectFit
        bgOffStateContainerView.addSubview(bgOffStateImageView)
        bgOffStateImageView.translatesAutoresizingMaskIntoConstraints = false
        
        bgOffStateImageView.topAnchor.constraint(equalTo: bgOffStateContainerView.topAnchor).isActive = true
        bgOffStateImageView.bottomAnchor.constraint(equalTo: bgOffStateContainerView.bottomAnchor).isActive = true
        bgOffStateImageView.rightAnchor.constraint(equalTo: bgOffStateContainerView.rightAnchor).isActive = true
        bgOffStateImageView.widthAnchor.constraint(equalTo: self.widthAnchor,
                                                   constant: -config.bgStateViewSideOffset * 2).isActive = true
    }
    
    @objc private func toggleSwitchState(_ gesture: UITapGestureRecognizer) {
        guard shouldToggleByTapGesture else { return }
        setOn(!isOn, animated: true)
        sendActions(for: .valueChanged)
    }
    
    @objc private func panRecognized(_ gesture: UIPanGestureRecognizer) {
        let translation = gesture.translation(in: self)
        
        var newXPosition = thumbView.frame.origin.x + translation.x
        if newXPosition <= 0 {
            newXPosition = 0
        } else if newXPosition > bounds.width - thumbView.bounds.width {
            newXPosition = bounds.width - thumbView.bounds.width
        }
        
        switch gesture.state {
        case .began, .changed:
            leftThumbViewAnchor?.constant = newXPosition
            gesture.setTranslation(CGPoint.zero, in: self)
            
            let occupancyPercentage = thumbView.center.x / bounds.width
            thumbOnStateImageView?.alpha = occupancyPercentage
            
        case .ended:
            if currentOccupancyPercentage >= config.panDeltaMovementInPercentages {
                setOn(!isOn, animated: true)
                sendActions(for: .valueChanged)
            } else {
                updateCurrentThumbPosition(animated: true)
            }
        default:
            updateCurrentThumbPosition(animated: true)
        }
    }
    
    private func updateCurrentThumbPosition(animated: Bool) {
        thumbView.layoutIfNeeded()
        let updatedXPosition = self.isOn ? self.bounds.width - thumbView.bounds.width : 0.0
        let currentAnimationDuration = currentOccupancyPercentage * config.fullChangeAnimationDuration
        leftThumbViewAnchor?.constant = updatedXPosition
        UIView.animate(withDuration: animated ? currentAnimationDuration : 0.0) {
            self.layoutIfNeeded()
            self.thumbOnStateImageView?.alpha = self.isOn ? 1.0 : 0.0
        }
    }
    
    private var currentOccupancyPercentage: Double {
        let halfThumbWidth = self.thumbView.bounds.width / 2.0
        let updatedXPosition = self.isOn ? self.bounds.width - halfThumbWidth : halfThumbWidth
        let delta = abs(updatedXPosition - thumbView.center.x)
        return Double(delta / bounds.width)
    }
}
