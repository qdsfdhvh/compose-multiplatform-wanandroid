package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavGraphBuilder
import me.seiko.jetpack.navigation2.Scene

interface ComposeScene : Scene {
  @Composable
  fun content(backStackEntry: NavBackStackEntry)

  companion object {
    operator fun invoke(
      route: String,
      content: @Composable (NavBackStackEntry) -> Unit
    ) = object : ComposeScene {

      override val route: String = route

      @Composable
      override fun content(backStackEntry: NavBackStackEntry) = content(backStackEntry)
    }
  }
}

fun NavGraphBuilder.scene(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = scene(
  ComposeScene(
    route = route,
    content = content
  )
)
