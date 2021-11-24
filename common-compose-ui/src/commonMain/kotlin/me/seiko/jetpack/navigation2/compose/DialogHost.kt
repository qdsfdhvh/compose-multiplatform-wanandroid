package me.seiko.jetpack.navigation2.compose

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput

@Composable
internal fun DialogHost(dialogNavigator: DialogNavigator) {
  dialogNavigator.backStacks.forEach { backStack ->
    if (backStack.scene is DialogScene) {
      DialogBox {
        backStack.scene.content(backStack)
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
