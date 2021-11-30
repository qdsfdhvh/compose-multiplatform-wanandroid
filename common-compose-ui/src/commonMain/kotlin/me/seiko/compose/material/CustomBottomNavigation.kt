package me.seiko.compose.material

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

@Composable
fun RowScope.CustomBottomNavigationItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  label: @Composable (() -> Unit)? = null,
) {
  Column(
    modifier = modifier
      .clickable(onClick = onClick)
      .alpha(if (selected) 1f else 0.6f)
      .fillMaxHeight()
      .weight(1f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    icon()
    label?.invoke()
  }
}
