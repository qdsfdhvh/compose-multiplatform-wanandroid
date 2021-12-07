package me.seiko.util

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
  fun init(antilog: Antilog = DebugAntilog()) {
    Napier.base(antilog)
  }

  fun d(tag: String? = null, throwable: Throwable? = null, lazyMessage: () -> String) {
    Napier.d(lazyMessage, throwable, tag)
  }

  fun i(tag: String? = null, throwable: Throwable? = null, lazyMessage: () -> String) {
    Napier.i(lazyMessage, throwable, tag)
  }

  fun w(tag: String? = null, throwable: Throwable? = null, lazyMessage: () -> String) {
    Napier.w(lazyMessage, throwable, tag)
  }
}
