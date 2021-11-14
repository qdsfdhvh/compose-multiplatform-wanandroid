import androidx.compose.ui.window.application
import me.seiko.chat.common.compose.ui.App
import me.seiko.jetpack.lifecycle.PreComposeWindow

fun main() = application {
  PreComposeWindow(onCloseRequest = ::exitApplication) {
    App()
  }
}
