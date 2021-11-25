package me.seiko.compose.component

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformInsets(
  control: NativeInsetsControl,
  color: NativeInsetsColor,
  content: @Composable () -> Unit
) {
  content()
}
