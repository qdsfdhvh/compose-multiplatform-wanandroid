package me.seiko.jetpack

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.awt.ComposeWindow

val LocalComposeWindow = staticCompositionLocalOf<ComposeWindow> { error("No ComposeWindow") }
