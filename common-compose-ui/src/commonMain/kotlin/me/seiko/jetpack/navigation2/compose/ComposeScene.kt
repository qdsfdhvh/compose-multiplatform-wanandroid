package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavGraphBuilder
import me.seiko.jetpack.navigation2.Scene

data class ComposeScene(
  override val route: String,
  val content: @Composable (NavBackStackEntry) -> Unit
) : Scene

fun NavGraphBuilder.scene(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = scene(
  ComposeScene(
    route = route,
    content = content
  )
)
