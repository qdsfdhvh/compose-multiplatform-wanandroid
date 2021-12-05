package me.seiko.kmp.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.compose.LocalImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
actual fun rememberNetworkImagePainter(data: Any): Painter {
  return rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
      .data(data)
      .crossfade(true)
      .build(),
    imageLoader = LocalImageLoader.current
  )
}
