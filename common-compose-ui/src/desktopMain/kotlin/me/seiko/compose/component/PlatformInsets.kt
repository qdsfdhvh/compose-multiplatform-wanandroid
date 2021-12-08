package me.seiko.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

actual fun Modifier.statusBarsHeight() = this
actual fun Modifier.navigationBarsHeight() = this

actual fun Modifier.statusBarsPadding() = this
actual fun Modifier.navigationBarsPadding() = this

actual val statusBarsHeightDp: Dp
  @Composable
  get() = 0.dp

actual val navigationBarsHeightDp: Dp
  @Composable
  get() = 0.dp
