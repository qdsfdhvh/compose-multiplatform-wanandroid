import me.seiko.chat.di.initKoin
import me.seiko.chat.runDesktop
import me.seiko.util.Logger

fun main() {
  Logger.init()

  initKoin()

  runDesktop()
}
