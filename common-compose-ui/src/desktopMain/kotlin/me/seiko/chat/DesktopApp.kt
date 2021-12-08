package me.seiko.chat

import androidx.compose.ui.window.application
import me.seiko.jetpack.lifecycle.PreComposeWindow

fun runDesktop() = application {
  PreComposeWindow(onCloseRequest = ::exitApplication) {
    App()
  }
}
