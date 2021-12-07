package me.seiko.compose.simple

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun SpacerWidth(value: Dp) {
  Spacer(Modifier.width(value))
}

@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun SpacerHeight(value: Dp) {
  Spacer(Modifier.height(value))
}
