package me.seiko.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.seiko.jetpack.LocalBackDispatcherOwner
import me.seiko.jetpack.dispatcher.BackHandler

@Composable
fun BackHandler(onBack: () -> Boolean) {
  val backDispatcher = checkNotNull(LocalBackDispatcherOwner.current) {
    "No OnBackPressedDispatcherOwner was provided via LocalBackDispatcherOwner"
  }.backDispatcher

  val backHandler = remember(onBack) {
    BackHandler(onBack)
  }

  val lifecycleOwner = LocalBackDispatcherOwner.current
  DisposableEffect(lifecycleOwner, backHandler) {
    backDispatcher.register(backHandler)
    onDispose {
      backDispatcher.unregister(backHandler)
    }
  }
}

@Composable
inline fun BackHandler(scope: CoroutineScope, crossinline onBack: suspend () -> Unit) {
  BackHandler(onBack = {
    scope.launch { onBack() }
    true
  })
}
