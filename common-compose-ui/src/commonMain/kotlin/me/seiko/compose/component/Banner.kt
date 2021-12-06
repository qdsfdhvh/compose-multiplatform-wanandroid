package me.seiko.compose.component

import androidx.annotation.IntRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Density
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign
import kotlin.math.withSign

@Composable
fun <T> Banner(
  list: List<T>,
  modifier: Modifier = Modifier,
  state: BannerState = rememberBannerState(pageCount = list.size),
  coroutineScope: CoroutineScope = rememberCoroutineScope(),
  loopDuration: Long = 0,
  content: @Composable BannerScope.(item: T, index: Int) -> Unit,
) {
  Banner(
    modifier = modifier,
    state = state,
    coroutineScope = coroutineScope,
    loopDuration = loopDuration,
    content = { content(list[page], page) }
  )
}

@Composable
fun Banner(
  state: BannerState,
  modifier: Modifier = Modifier,
  coroutineScope: CoroutineScope = rememberCoroutineScope(),
  loopDuration: Long = 0,
  content: @Composable BannerScope.() -> Unit
) {
  var pageSize by remember { mutableStateOf(0) }
  var isTouching by remember { mutableStateOf(false) }

  val currentPage = state.currentPage
  val currentPageOffset = state.currentPageOffset

  Layout(
    modifier = modifier
      .clip(RectangleShape) // 裁掉边界外的图案
      .onGloballyPositioned {
        pageSize = it.size.width
      }
      .pointerInput(Unit) {
        detectHorizontalDragGestures(
          onDragStart = {
            isTouching = true
          },
          onDragEnd = {
            isTouching = false
            coroutineScope.launch {
              state.dragEnd(pageSize)
            }
          },
          onDragCancel = {
            isTouching = false
            coroutineScope.launch {
              state.dragEnd(pageSize)
            }
          },
          onHorizontalDrag = { change, dragAmount ->
            val newPos = with(state) {
              val pos = pageSize * currentPageOffset
              val max = pageSize
              val min = -pageSize
              (pos + dragAmount).coerceIn(min.toFloat(), max.toFloat())
            }
            if (newPos != 0f) {
              change.consumePositionChange()

              state.addPosition(change.uptimeMillis, change.position)
              coroutineScope.launch {
                state.snapToOffset(newPos / pageSize)
              }
            }
          }
        )
      },
    content = {
      if (state.pageCount == 0) return@Layout

      val two = state.currentPage
      val one = if (two == state.firstPageIndex) state.lastPageIndex else two - 1
      val three = if (two == state.lastPageIndex) state.firstPageIndex else two + 1

      for (page in arrayOf(one, two, three)) {
        key(page) {
          Box(modifier = BannerData(page).fillMaxWidth().wrapContentSize()) {
            BannerScope(page, state).content()
          }
        }
      }
    }
  ) { measurables, constraints ->
    layout(constraints.maxWidth, constraints.maxHeight) {
      measurables.forEach { measurable ->
        val placeable = measurable.measure(constraints)
        val page = measurable.page

        val xCenterOffset = (constraints.maxWidth - placeable.width) / 2
        val xItemOffset = ((page - currentPage + currentPageOffset) * pageSize).roundToInt()
        placeable.place(
          x = xCenterOffset + xItemOffset,
          y = 0
        )
      }
    }
  }

  if (loopDuration > 0 && !isTouching) {
    LaunchedEffect(loopDuration) {
      while (isActive) {
        delay(loopDuration)
        state.nextPage()
      }
    }
  }
}

@Composable
fun rememberBannerState(
  @IntRange(from = 0) pageCount: Int,
  @IntRange(from = 0) currentPage: Int = 0,
): BannerState {
  return rememberSaveable(saver = BannerState.Saver) {
    BannerState(pageCount, currentPage)
  }.apply {
    this.pageCount = pageCount
  }
}

class BannerState(
  @IntRange(from = 0) pageCount: Int,
  @IntRange(from = 0) currentPage: Int,
) {

  private val _pageCount = mutableStateOf(pageCount)
  private val _currentPage = mutableStateOf(currentPage)

  @get:IntRange(from = 0)
  var pageCount: Int
    get() = _pageCount.value
    internal set(value) {
      _pageCount.value = value
    }

  @get:IntRange(from = 0)
  var currentPage: Int
    get() = _currentPage.value
    internal set(value) {
      _currentPage.value = value
    }

  internal inline val firstPageIndex: Int
    get() = 0

  internal inline val lastPageIndex: Int
    get() = (pageCount - 1).coerceAtLeast(0)

  private var _currentPageOffset = Animatable(0f).apply {
    updateBounds(-1f, 1f)
  }

  val currentPageOffset: Float
    get() = _currentPageOffset.value

  private val velocityTracker = VelocityTracker()

  suspend fun dragEnd(pageSize: Int) {
    val velocity = velocityTracker.calculateVelocity()
    fling(velocity.x / pageSize)
  }

  private suspend fun fling(velocity: Float) {
    val currentOffset = currentPageOffset
    when {
      currentOffset.sign == velocity.sign && (
        velocity.absoluteValue > 1.5f ||
          currentOffset.absoluteValue > 0.5f &&
          currentOffset.absoluteValue < 1.5f
        ) -> {
        _currentPageOffset.animateTo(1f.withSign(velocity))
      }
      else -> {
        _currentPageOffset.animateTo(0f)
      }
    }
    selectPage()
  }

  suspend fun snapToOffset(offset: Float) {
    _currentPageOffset.snapTo(offset)
  }

  private suspend fun selectPage() {
    val piv = currentPageOffset.roundToInt()
    _currentPageOffset.snapTo(0f)
    when {
      piv < 0 -> {
        if (currentPage == lastPageIndex) currentPage = firstPageIndex
        else currentPage += 1
      }
      piv > 0 -> {
        if (currentPage == firstPageIndex) currentPage = lastPageIndex
        else currentPage -= 1
      }
    }
  }

  fun addPosition(timeMillis: Long, position: Offset) {
    velocityTracker.addPosition(timeMillis = timeMillis, position = position)
  }

  suspend fun prevPage() {
    _currentPageOffset.animateTo(1f, animationSpec = tween(320))
    selectPage()
  }

  suspend fun nextPage() {
    _currentPageOffset.animateTo(-1f, animationSpec = tween(320))
    selectPage()
  }

  companion object {
    val Saver: Saver<BannerState, *> = listSaver(
      save = {
        listOf(it.pageCount, it.currentPage)
      },
      restore = {
        BannerState(
          pageCount = it[0],
          currentPage = it[1],
        )
      }
    )
  }
}

class BannerScope internal constructor(
  val page: Int,
  private val state: BannerState
) {
  val currentPage: Int
    get() = state.currentPage

  val pageCount: Int
    get() = state.pageCount
}

@Immutable
private data class BannerData(val page: Int) : ParentDataModifier {
  override fun Density.modifyParentData(parentData: Any?): Any = this@BannerData
}

private val Measurable.page: Int
  get() = (parentData as? BannerData)?.page ?: error("no PageData for measurable $this")
