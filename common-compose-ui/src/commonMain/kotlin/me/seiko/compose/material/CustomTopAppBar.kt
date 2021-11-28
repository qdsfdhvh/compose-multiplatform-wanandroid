package me.seiko.compose.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.seiko.compose.component.statusBarsPadding

@Composable
fun CustomTopAppBar(
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationIcon: @Composable (() -> Unit)? = null,
  actions: @Composable RowScope.() -> Unit = {},
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
) {
  TopAppBar(
    title = title,
    modifier = modifier.background(backgroundColor).statusBarsPadding(),
    navigationIcon = navigationIcon,
    actions = actions,
    backgroundColor = Color.Transparent,
    contentColor = contentColor,
    elevation = 0.dp,
  )
}
