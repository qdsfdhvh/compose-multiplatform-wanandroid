package me.seiko.chat.common.compose.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.seiko.chat.common.compose.ui.scene.home.HomeScene
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.rememberNavController

@Composable
fun App() {
  MaterialTheme {
    val navController = rememberNavController()

    CompositionLocalProvider(
      LocalNavController provides navController
    ) {
      HomeScene()
    }
  }
}
