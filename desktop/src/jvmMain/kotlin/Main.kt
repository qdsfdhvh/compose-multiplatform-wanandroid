import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.seiko.chat.common.compose.ui.App

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    MaterialTheme {
      App()
    }
  }
}
