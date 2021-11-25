package me.seiko.chat.common.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import me.seiko.chat.common.compose.ui.theme.AppTheme
import me.seiko.util.Logger

@Composable
fun App() {
  LaunchedEffect(Unit) {
    Logger.init()
  }

  AppTheme {
    Route()
  }
}
