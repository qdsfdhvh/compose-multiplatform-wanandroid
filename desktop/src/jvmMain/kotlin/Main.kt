import androidx.compose.ui.window.application
import me.seiko.chat.App
import me.seiko.di.initKoin
import me.seiko.jetpack.lifecycle.PreComposeWindow

fun main() = application {
  initKoin()

  PreComposeWindow(onCloseRequest = ::exitApplication) {
    App()
  }
}
