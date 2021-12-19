package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.NavControllerViewModel
import me.seiko.jetpack.navigation2.NavDestination

@Composable
fun NavController.SceneContent(route: String) {
  val owner = checkNotNull(LocalViewModelStoreOwner.current) {
    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
  }

  // save id to get NavControllerViewModel correctly
  val entryId = rememberSaveable(route, owner) {
    NavDestination.getEntryId()
  }

  val entry = remember(entryId) {
    val navDestination = findNavDestination(route) ?: return@remember null
    navDestination.createEntry(
      id = entryId,
      viewModel = NavControllerViewModel.create(owner.viewModelStore)
    )
  }

  if (entry != null && entry.scene is ComposeScene) {
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
