package me.seiko.compose.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
fun TextSubTitle2(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.onSurface
) {
  Text(
    text = text,
    modifier = modifier,
    color = color,
    style = MaterialTheme.typography.subtitle2
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

@Composable
fun TextTag(
  text: String,
  modifier: Modifier = Modifier,
) {
  Text(
    text = text,
    modifier = modifier
      .background(MaterialTheme.colors.surface, CircleShape)
      .padding(horizontal = 8.dp, vertical = 4.dp),
    style = MaterialTheme.typography.caption
  )
}
