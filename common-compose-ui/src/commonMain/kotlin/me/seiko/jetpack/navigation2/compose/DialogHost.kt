package me.seiko.jetpack.navigation2.compose

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner

@Composable
internal fun DialogHost(dialogNavigator: DialogNavigator) {
  dialogNavigator.backStacks.forEach { backStack ->
    if (backStack.scene is DialogScene) {

      DisposableEffect(Unit) {
        backStack.onActive()
        onDispose { backStack.onInActive() }
      }

      CompositionLocalProvider(
        LocalViewModelStoreOwner provides backStack,
        LocalLifecycleOwner provides backStack,
      ) {
        DialogBox {
          backStack.scene.content(backStack)
        }
      }
    }
  }
}

@Composable
private fun DialogBox(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier.pointerInput(Unit) {
      forEachGesture {
        awaitPointerEventScope {
          awaitPointerEvent().changes.forEach {
            it.consumeAllChanges()
          }
        }
      }
    },
    content = content
  )
}
