package me.seiko.compose.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun RowScope.CustomBottomNavigationItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier,
  label: @Composable (() -> Unit)?,
) {
  BottomNavigationItem(
    selected = selected,
    onClick = onClick,
    icon = icon,
    modifier = modifier,
    label = label,
  )
}
