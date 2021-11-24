package me.seiko.jetpack.navigation2.compose

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.seiko.jetpack.LocalBackDispatcherOwner
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.NavGraphBuilder

@Composable
fun NavHost(
  navController: NavController,
  initialRoute: String,
  modifier: Modifier = Modifier,
  builder: NavGraphBuilder.() -> Unit = {},
) {
  val lifecycleOwner = checkNotNull(LocalLifecycleOwner.current) {
    "NavHost requires a lifecycleOwner to be provided via LocalLifecycleOwner"
  }
  navController.lifecycleOwner = lifecycleOwner

  val backDispatcherOwner = LocalBackDispatcherOwner.current
  if (backDispatcherOwner != null) {
    navController.backDispatcher = backDispatcherOwner.backDispatcher
  }

  LaunchedEffect(initialRoute, builder) {
    val graph = NavGraphBuilder(initialRoute).apply(builder).build()
    navController.graph = graph
  }

  val navigator = navController.navigatorProvider.get(ComposeNavigator::class) ?: return

  val backStack = navigator.backStacks.lastOrNull()
  if (backStack != null && backStack.scene is ComposeScene) {
    Crossfade(backStack.id, modifier) {
      backStack.scene.content(backStack)
    }
  }

  val dialogNavigator = navController.navigatorProvider.get(DialogNavigator::class) ?: return
  DialogHost(dialogNavigator)
}
