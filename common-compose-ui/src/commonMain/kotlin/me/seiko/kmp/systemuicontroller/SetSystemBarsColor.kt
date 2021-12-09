package me.seiko.kmp.systemuicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetSystemBarsColor(darkIcons: Boolean)

@Composable
expect fun SetSystemBarsColor(color: Color, darkIcons: Boolean)
