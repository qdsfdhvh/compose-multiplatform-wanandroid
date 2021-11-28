package me.seiko.compose.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.seiko.compose.component.navigationBarsPadding

@Composable
fun CustomBottomNavigation(
  modifier: Modifier = Modifier,
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  content: @Composable RowScope.() -> Unit
) {
  BottomNavigation(
    modifier = modifier.background(backgroundColor).navigationBarsPadding(),
    backgroundColor = Color.Transparent,
    contentColor = contentColor,
    elevation = 0.dp,
    content = content,
  )
}
