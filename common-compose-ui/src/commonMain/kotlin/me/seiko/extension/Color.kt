package me.seiko.extension

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Color.withElevation(elevation: Dp = AppBarDefaults.TopAppBarElevation): Color {
  return LocalElevationOverlay.current?.apply(
    color = this,
    elevation = elevation
  ) ?: this
}
