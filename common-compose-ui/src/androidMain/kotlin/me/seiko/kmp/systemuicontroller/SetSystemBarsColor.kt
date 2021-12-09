package me.seiko.kmp.systemuicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color

@Composable
actual fun SetSystemBarsColor(darkIcons: Boolean) {
  SetSystemBarsColor(Color.Transparent, darkIcons)
}

@Composable
actual fun SetSystemBarsColor(color: Color, darkIcons: Boolean) {
  val systemUiController = rememberSystemUiController()
  SideEffect {
    systemUiController.setSystemBarsColor(color, darkIcons)
  }
}
