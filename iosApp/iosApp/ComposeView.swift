//
//  ComposeView.swift
//  iosApp
//
//  Created by guanpj on 2023/7/1.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import UIKit

// Custom ViewController wrapper to control status bar appearance
class ComposeViewController: UIViewController {
    private let composeViewController: UIViewController

    init(composeViewController: UIViewController) {
        self.composeViewController = composeViewController
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Add compose view controller as child
        addChild(composeViewController)
        view.addSubview(composeViewController.view)
        composeViewController.view.frame = view.bounds
        composeViewController.view.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        composeViewController.didMove(toParent: self)
    }

    // Force light status bar (white text)
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }

    func makeUIViewController(context: Context) -> some UIViewController {
        let composeVC = Main_iosKt.MainController()
        return ComposeViewController(composeViewController: composeVC)
    }
}
