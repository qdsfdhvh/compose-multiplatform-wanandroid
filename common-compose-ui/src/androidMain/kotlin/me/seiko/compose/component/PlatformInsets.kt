package me.seiko.compose.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding

actual fun Modifier.statusBarsHeight() = this.statusBarsHeight(0.dp)
actual fun Modifier.navigationBarsHeight() = this.navigationBarsHeight(0.dp)

actual fun Modifier.statusBarsPadding() = this.statusBarsPadding()
actual fun Modifier.navigationBarsPadding() = this.navigationBarsPadding()
