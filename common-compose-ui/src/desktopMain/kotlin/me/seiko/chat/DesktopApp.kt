package me.seiko.chat

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import me.seiko.jetpack.lifecycle.PreComposeWindow
import me.seiko.kmp.LocalResLoader
import me.seiko.kmp.resource.ResLoader

fun runDesktop() = application {
  PreComposeWindow(onCloseRequest = ::exitApplication) {
    CompositionLocalProvider(
      LocalResLoader provides ResLoader()
    ) {
      App()
    }
  }
}
