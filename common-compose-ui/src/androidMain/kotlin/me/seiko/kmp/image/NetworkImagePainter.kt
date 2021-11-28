package me.seiko.kmp.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter

@Composable
actual fun rememberNetworkImagePainter(data: Any): Painter {
  return rememberImagePainter(
    data = data,
  )
}
