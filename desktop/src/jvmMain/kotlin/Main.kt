import me.seiko.chat.di.initKoin
import me.seiko.chat.runDesktop
import me.seiko.compose.web.DesktopCustomWebViewFactory
import me.seiko.compose.web.DesktopCustomWebViewFactoryImpl
import me.seiko.util.Logger

fun main() {
  Logger.init()

  initKoin()

  DesktopCustomWebViewFactory.register(DesktopCustomWebViewFactoryImpl)

  runDesktop()
}
