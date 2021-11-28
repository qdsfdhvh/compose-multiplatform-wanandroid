package me.seiko.compose.component

import androidx.compose.ui.Modifier

expect fun Modifier.statusBarsHeight(): Modifier
expect fun Modifier.navigationBarsHeight(): Modifier

expect fun Modifier.statusBarsPadding(): Modifier
expect fun Modifier.navigationBarsPadding(): Modifier
