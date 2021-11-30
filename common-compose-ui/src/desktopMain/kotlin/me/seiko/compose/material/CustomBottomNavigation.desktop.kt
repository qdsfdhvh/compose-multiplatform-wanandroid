package me.seiko.compose.material

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.mouseClickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun RowScope.CustomBottomNavigationItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier,
  label: @Composable (() -> Unit)?,
) {
  Column(
    modifier = modifier
      .mouseClickable { onClick() }
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
