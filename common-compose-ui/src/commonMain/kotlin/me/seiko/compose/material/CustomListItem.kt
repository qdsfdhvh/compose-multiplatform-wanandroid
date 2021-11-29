package me.seiko.compose.material

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomListItem(
  modifier: Modifier = Modifier,
  icon: @Composable (() -> Unit)? = null,
  secondaryText: @Composable (() -> Unit)? = null,
  singleLineSecondaryText: Boolean = true,
  overlineText: @Composable (() -> Unit)? = null,
  trailing: @Composable (() -> Unit)? = null,
  text: @Composable () -> Unit
) {
  ListItem(
    modifier = modifier,
    icon = icon,
    secondaryText = secondaryText,
    singleLineSecondaryText = singleLineSecondaryText,
    overlineText = overlineText,
    trailing = trailing,
    text = text
  )
}
