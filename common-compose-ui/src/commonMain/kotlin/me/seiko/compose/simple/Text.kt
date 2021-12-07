package me.seiko.compose.simple

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TextSubTitle1(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = LocalContentColor.current
) {
  Text(
    text = text,
    modifier = modifier,
    color = color,
    style = MaterialTheme.typography.subtitle1
  )
}

@Composable
fun TextCaption(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = LocalContentColor.current.copy(ContentAlpha.high)
) {
  Text(
    text = text,
    modifier = modifier,
    color = color,
    style = MaterialTheme.typography.caption
  )
}
