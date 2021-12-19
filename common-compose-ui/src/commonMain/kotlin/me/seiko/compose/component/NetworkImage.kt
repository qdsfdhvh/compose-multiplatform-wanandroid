package me.seiko.compose.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import me.seiko.kmp.image.rememberNetworkImagePainter

@Composable
fun NetworkImage(
  data: Any,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Crop,
) {
  Image(
    painter = rememberNetworkImagePainter(data),
    contentDescription = contentDescription,
    modifier = modifier,
    alignment = alignment,
    contentScale = contentScale,
  )
}
