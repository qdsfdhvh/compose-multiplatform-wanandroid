package me.seiko.jetpack.navigation2.compose

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.NavGraphBuilder

@Composable
fun NavHost(
  navController: NavController,
  initialRoute: String,
  modifier: Modifier = Modifier,
  builder: NavGraphBuilder.() -> Unit = {},
) {
  val navigator = remember { ComposeNavigator() }

  DisposableEffect(navController) {
    navController.getNavigatorHolder().run {
      setNavigator(navigator)
      onDispose { removeNavigator() }
    }
  }

  LaunchedEffect(initialRoute, builder) {
    val graph = NavGraphBuilder(initialRoute).apply(builder).build()
    navController.graph = graph
  }

  val backStacks by navigator.backStacks.collectAsState()

  val backStack = backStacks.lastOrNull()
  if (backStack != null && backStack.scene is ComposeScene) {
    Crossfade(backStack.id, modifier) {
      backStack.scene.content(backStack)
    }
  }
}
