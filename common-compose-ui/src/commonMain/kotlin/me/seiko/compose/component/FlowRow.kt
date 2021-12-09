package me.seiko.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun FlowRow(
  modifier: Modifier = Modifier,
  contentPadding: Dp = 0.dp,
  content: @Composable () -> Unit
) {
  Layout(content, modifier) { measurables, constraints ->

    var xPosition = 0
    var yPosition = 0

    var rowMaxHeight = 0

    val contentPaddingPx = contentPadding.toPx().roundToInt()

    val pair = measurables.map { measurable ->
      val placeable = measurable.measure(constraints)

      if (xPosition + placeable.width > constraints.maxWidth) {
        xPosition = 0
        yPosition += (rowMaxHeight + contentPaddingPx)
        rowMaxHeight = 0
      }

      val size = IntSize(xPosition, yPosition)

      xPosition += (placeable.width + contentPaddingPx)
      rowMaxHeight = max(rowMaxHeight, placeable.height)

      placeable to size
    }

    layout(constraints.maxWidth, yPosition + rowMaxHeight) {
      pair.forEach { (placeable, size) ->
        placeable.placeRelative(size.width, size.height)
      }
    }
  }
}
