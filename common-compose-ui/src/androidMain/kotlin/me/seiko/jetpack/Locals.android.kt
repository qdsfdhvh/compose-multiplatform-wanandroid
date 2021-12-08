package me.seiko.jetpack

import android.app.Activity
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<Activity> { error("No Activity") }
