package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavDestination
import me.seiko.jetpack.navigation2.NavGraphBuilder
import me.seiko.jetpack.navigation2.RegexScene
import me.seiko.jetpack.navigation2.RouteScene

interface ComposeScene {
  val content: @Composable (NavBackStackEntry) -> Unit
}

interface DialogScene {
  val content: @Composable (NavBackStackEntry) -> Unit
}

fun NavGraphBuilder.scene(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = composable(
  NavDestination(
    scene = object : ComposeScene, RouteScene {
      override val route: String = route
      override val content: @Composable (NavBackStackEntry) -> Unit = content
    },
    navigator = provider[ComposeNavigator::class]
  )
)

fun NavGraphBuilder.scene(
  regex: Regex,
  content: @Composable (NavBackStackEntry) -> Unit
) = composable(
  NavDestination(
    scene = object : ComposeScene, RegexScene {
      override val regex: Regex = regex
      override val content: @Composable (NavBackStackEntry) -> Unit = content
    },
    navigator = provider[ComposeNavigator::class]
  )
)

fun NavGraphBuilder.dialog(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) = composable(
  NavDestination(
    scene = object : DialogScene, RouteScene {
      override val route: String = route
      override val content: @Composable (NavBackStackEntry) -> Unit = content
    },
    navigator = provider[DialogNavigator::class]
  )
)
