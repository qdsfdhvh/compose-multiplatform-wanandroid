package me.seiko.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class NativeInsetsControl(
  val extendToTop: Boolean = false,
  val extendToBottom: Boolean = false,
  val extendToStart: Boolean = false,
  val extendToEnd: Boolean = false,
)

data class NativeInsetsColor(
  val top: Color = Color.Transparent,
  val bottom: Color = Color.Transparent,
  val start: Color = Color.Transparent,
  val end: Color = Color.Transparent,
)

@Composable
expect fun PlatformInsets(
  control: NativeInsetsControl,
  color: NativeInsetsColor,
  content: @Composable () -> Unit
)
