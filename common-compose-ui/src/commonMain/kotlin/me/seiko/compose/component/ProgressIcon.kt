package me.seiko.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.seiko.compose.material.CustomIcon

@Composable
fun ProgressIcon(
  image: ImageVector,
  progress: Float,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  progressColor: Color = MaterialTheme.colors.primarySurface,
) {
  CustomIcon(
    image = image,
    modifier = modifier
      .shadow(4.dp, shape = CircleShape)
      .clickable { onClick() }
      .drawWithContent {
        drawContent()
        if (progress > 0f) {
          drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = progress * 360f,
            useCenter = false,
            style = Stroke(10f, cap = StrokeCap.Round)
          )
        }
      }
      .background(MaterialTheme.colors.surface)
      .padding(10.dp)
  )
}
