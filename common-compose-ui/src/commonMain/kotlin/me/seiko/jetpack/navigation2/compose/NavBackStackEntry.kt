package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.SaveableStateHolder
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.navigation2.NavBackStackEntry

@Composable
fun NavBackStackEntry.LocalProvider(
  saveableStateHolder: SaveableStateHolder,
  content: @Composable () -> Unit
) {
  saveableStateHolder.SaveableStateProvider("navigation.saveable.$id") {
    CompositionLocalProvider(
      LocalViewModelStoreOwner provides this,
      LocalLifecycleOwner provides this,
    ) {
      content()
    }
  }
}
