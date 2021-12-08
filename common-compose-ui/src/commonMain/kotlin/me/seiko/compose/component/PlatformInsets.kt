package me.seiko.compose.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

expect fun Modifier.statusBarsHeight(): Modifier
expect fun Modifier.navigationBarsHeight(): Modifier

expect fun Modifier.statusBarsPadding(): Modifier
expect fun Modifier.navigationBarsPadding(): Modifier

expect val statusBarsHeightDp: Dp
expect val navigationBarsHeightDp: Dp
