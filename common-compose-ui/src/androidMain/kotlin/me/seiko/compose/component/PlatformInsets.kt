package me.seiko.compose.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.seiko.compose.insets.navigationBarsHeight
import me.seiko.compose.insets.navigationBarsPadding
import me.seiko.compose.insets.statusBarsHeight
import me.seiko.compose.insets.statusBarsPadding

actual fun Modifier.statusBarsHeight() = this.statusBarsHeight(0.dp)
actual fun Modifier.navigationBarsHeight() = this.navigationBarsHeight(0.dp)

actual fun Modifier.statusBarsPadding() = this.statusBarsPadding()
actual fun Modifier.navigationBarsPadding() = this.navigationBarsPadding()
