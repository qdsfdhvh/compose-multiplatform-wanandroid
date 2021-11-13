import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.seiko.chat.common.compose.ui.App

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    DesktopMaterialTheme {
      App()
    }
  }
}
