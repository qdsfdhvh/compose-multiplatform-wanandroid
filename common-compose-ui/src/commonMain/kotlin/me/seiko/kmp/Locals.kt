package me.seiko.kmp

import androidx.compose.runtime.staticCompositionLocalOf
import me.seiko.kmp.resource.ResLoader

val LocalResLoader = staticCompositionLocalOf<ResLoader> { error("No ResLoader") }
