package me.seiko.chat.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(if (darkTheme) darkColors else lightColors) {
    content()
  }
}

val lightColors = lightColors(
  primary = Color(0XFF1C68F3),
  primaryVariant = Color(0XFF1C68F3),
  secondary = Color(0XFF1C68F3),
)

val darkColors = darkColors(
  primary = Color(0XFF5CB0FF),
  primaryVariant = Color(0XFF5CB0FF),
  secondary = Color(0XFF5CB0FF),
)
