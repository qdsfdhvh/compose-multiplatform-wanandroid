package me.seiko.util

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
  fun init(antilog: Antilog = DebugAntilog()) {
    Napier.base(antilog)
  }

  fun d(lazyMessage: () -> String) {
    Napier.d(lazyMessage)
  }

  fun w(e: Throwable, lazyMessage: () -> String) {
    Napier.w(lazyMessage, e)
  }
}
