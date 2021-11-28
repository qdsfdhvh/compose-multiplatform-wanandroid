package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.NavControllerViewModel

@Composable
fun NavController.SceneContent(route: String) {
  val owner = checkNotNull(LocalViewModelStoreOwner.current) {
    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
  }

  val entry = remember(route, owner) {
    val navDestination = findNavDestination(route)
    navDestination.createEntry(NavControllerViewModel.create(owner.viewModelStore))
  }

  if (entry.scene is ComposeScene) {
    DisposableEffect(Unit) {
      entry.onActive()
      onDispose { entry.onInActive() }
    }

    CompositionLocalProvider(
      LocalViewModelStoreOwner provides entry,
      LocalLifecycleOwner provides entry
    ) {
      entry.scene.content(entry)
    }
  }
}
