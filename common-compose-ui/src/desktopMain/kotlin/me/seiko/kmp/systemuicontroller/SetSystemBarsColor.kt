package me.seiko.kmp.systemuicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
actual fun SetSystemBarsColor(darkIcons: Boolean) = Unit

@Composable
actual fun SetSystemBarsColor(color: Color, darkIcons: Boolean) = Unit
