package me.seiko.chat.common.compose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import me.seiko.compose.component.NativeInsetsColor
import me.seiko.compose.component.NativeInsetsControl
import me.seiko.compose.component.PlatformInsets

@Composable
fun AppTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme {
    content()
  }
}

@Composable
fun AppScene(
  extendViewIntoStatusBar: Boolean = false,
  extendViewIntoNavigationBar: Boolean = false,
  content: @Composable () -> Unit
) {
  PlatformInsets(
    control = NativeInsetsControl(
      extendToTop = extendViewIntoStatusBar,
      extendToBottom = extendViewIntoNavigationBar,
      extendToStart = extendViewIntoNavigationBar,
      extendToEnd = extendViewIntoNavigationBar,
    ),
    color = NativeInsetsColor(
      top = MaterialTheme.colors.primary,
      start = MaterialTheme.colors.primary,
      end = MaterialTheme.colors.primary,
      bottom = MaterialTheme.colors.primary,
    ),
  ) {
    content()
  }
}
