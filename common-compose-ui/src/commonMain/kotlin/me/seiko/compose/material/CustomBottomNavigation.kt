package me.seiko.compose.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.seiko.compose.component.navigationBarsPadding

@Composable
fun CustomBottomNavigation(
  modifier: Modifier = Modifier,
  isDivider: Boolean = true,
  backgroundColor: Color = MaterialTheme.colors.background,
  contentColor: Color = contentColorFor(backgroundColor),
  content: @Composable RowScope.() -> Unit
) {
  Column {
    if (isDivider) Divider()
    BottomNavigation(
      modifier = modifier.background(backgroundColor).navigationBarsPadding(),
      backgroundColor = Color.Transparent,
      contentColor = contentColor,
      elevation = 0.dp,
      content = content,
    )
  }
}

@Composable
fun RowScope.CustomBottomNavigationItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  label: @Composable (() -> Unit)? = null,
  selectedContentColor: Color = LocalContentColor.current,
  unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium)
) {
  BottomNavigationItem(
    selected = selected,
    onClick = onClick,
    icon = icon,
    modifier = modifier,
    label = label,
    selectedContentColor = selectedContentColor,
    unselectedContentColor = unselectedContentColor,
  )
}
