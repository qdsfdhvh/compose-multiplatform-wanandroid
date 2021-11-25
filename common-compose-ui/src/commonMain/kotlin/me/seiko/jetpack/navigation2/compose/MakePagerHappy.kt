package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.navigation2.NavController

@Composable
fun NavController.SceneContent(route: String) {
  val entry = remember(route) {
    val navDestination = findNavDestination(route)
    navDestination.createEntry("", viewModel!!)
  }
  if (entry.scene is ComposeScene) {
    DisposableEffect(Unit) {
      entry.onActive()
      onDispose { entry.onInActive() }
      // ?onDestroy
    }

    CompositionLocalProvider(
      LocalViewModelStoreOwner provides entry,
      LocalLifecycleOwner provides entry
    ) {
      entry.scene.content(entry)
    }
  }
}
