package me.seiko.compose.material

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomIcon(
  image: ImageVector,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
) {
  Icon(
    imageVector = image,
    contentDescription = contentDescription,
    modifier = modifier,
  )
}
