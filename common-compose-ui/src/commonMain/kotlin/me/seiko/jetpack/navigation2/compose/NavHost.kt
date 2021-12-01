package me.seiko.jetpack.navigation2.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import me.seiko.jetpack.LocalBackDispatcherOwner
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
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
  val viewModelStoreOwner = LocalViewModelStoreOwner.current
  val backDispatcherOwner = LocalBackDispatcherOwner.current

  LaunchedEffect(lifecycleOwner, viewModelStoreOwner, backDispatcherOwner) {
    navController.lifecycleOwner = lifecycleOwner
    navController.setViewModelStore(viewModelStoreOwner.viewModelStore)
    if (backDispatcherOwner != null) {
      navController.backDispatcher = backDispatcherOwner.backDispatcher
    }
  }

  LaunchedEffect(initialRoute, builder) {
    val graph = NavGraphBuilder(navController.navigatorProvider, initialRoute).apply(builder).build()
    navController.graph = graph
  }

  val saveableStateHolder = rememberSaveableStateHolder()

  val navigator = navController.navigatorProvider.getNavigator(ComposeNavigator::class) ?: return

  val backStack = navigator.backStacks.lastOrNull()
  if (backStack != null && backStack.scene is ComposeScene) {
    Box(modifier) { // TODO Crossfade will multi run, error with saveableStateHolder

      DisposableEffect(Unit) {
        backStack.onActive()
        onDispose { backStack.onInActive() }
      }

      backStack.LocalProvider(saveableStateHolder) {
        backStack.scene.content(backStack)
      }
    }
  }

  val dialogNavigator = navController.navigatorProvider.getNavigator(DialogNavigator::class) ?: return
  DialogHost(dialogNavigator)
}
