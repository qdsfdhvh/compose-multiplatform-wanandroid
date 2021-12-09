package me.seiko.compose.component

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDownIgnoreConsumed
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import me.seiko.compose.pager.HorizontalPager
import me.seiko.compose.pager.PagerScope
import me.seiko.compose.pager.rememberPagerState

/**
 * THX: https://github.com/google/accompanist/blob/main/sample/src/main/java/com/google/accompanist/sample/pager/HorizontalPagerLoopingSample.kt
 */
@Composable
fun <T> Banner(
  list: List<T>,
  modifier: Modifier = Modifier,
  loopDuration: Long = 0,
  content: @Composable PagerScope.(item: T, index: Int) -> Unit,
) {
  Banner(
    pageCount = list.size,
    modifier = modifier,
    loopDuration = loopDuration,
    content = { content(list[it], it) }
  )
}

@Composable
fun Banner(
  pageCount: Int,
  modifier: Modifier = Modifier,
  loopDuration: Long = 0,
  content: @Composable PagerScope.(Int) -> Unit
) {
  val startIndex = Int.MAX_VALUE / 2
  val state = rememberPagerState(initialPage = startIndex)

  var isTouching by remember { mutableStateOf(false) }

  HorizontalPager(
    count = Int.MAX_VALUE,
    state = state,
    modifier = modifier
      .pointerInput(Unit) {
        forEachGesture {
          awaitPointerEventScope {
            var event = awaitPointerEvent(PointerEventPass.Initial)
            if (!isTouching && event.changes.any { it.changedToDownIgnoreConsumed() }) {
              isTouching = true
            }

            while (isTouching) {
              event = awaitPointerEvent(PointerEventPass.Final)
              if (event.changes.all { it.changedToUpIgnoreConsumed() }) {
                isTouching = false
              }
            }
          }
        }
      },
  ) { index ->
    val page = (index - startIndex).floorMod(pageCount)
    content(page)
  }

  if (loopDuration > 0 && !isTouching) {
    LaunchedEffect(loopDuration) {
      while (isActive) {
        delay(loopDuration)
        state.animateScrollToPage(state.currentPage + 1)
      }
    }
  }
}

private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}
