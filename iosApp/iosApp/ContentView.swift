import SwiftUI
import shared

struct ContentView: View {
	var body: some View {
        ComposeView()
            .ignoresSafeArea(.all) // Extend content to status bar (immersive)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ComposeView()
            .ignoresSafeArea(.all)
	}
}
