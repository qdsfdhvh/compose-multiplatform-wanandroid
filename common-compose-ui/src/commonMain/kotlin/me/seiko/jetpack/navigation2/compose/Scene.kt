package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavDestination
import me.seiko.jetpack.navigation2.NavGraphBuilder
import me.seiko.jetpack.navigation2.Scene

data class ComposeScene(
  override val route: String,
  val content: @Composable (NavBackStackEntry) -> Unit
) : Scene

data class DialogScene(
  override val route: String,
  val content: @Composable (NavBackStackEntry) -> Unit
) : Scene

fun NavGraphBuilder.scene(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = composable(
  NavDestination(
    scene = ComposeScene(
      route = route,
      content = content
    ),
    navigator = provider[ComposeNavigator::class]
  )
)

fun NavGraphBuilder.dialog(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = composable(
  NavDestination(
    scene = DialogScene(
      route = route,
      content = content
    ),
    navigator = provider[DialogNavigator::class]
  )
)
