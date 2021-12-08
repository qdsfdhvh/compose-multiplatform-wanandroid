package me.seiko.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
  fun init() {
    Napier.base(DebugAntilog())
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
