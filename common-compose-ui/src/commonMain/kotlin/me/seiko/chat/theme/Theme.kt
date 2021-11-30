package me.seiko.chat.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme {
    content()
  }
}
