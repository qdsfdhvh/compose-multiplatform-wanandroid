package me.seiko.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> TextTabRow(
  selectedIndex: Int,
  list: List<T>,
  mapToString: (T) -> String,
  onItemClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
  selectedContentColor: Color = MaterialTheme.colors.primarySurface,
  unselectedContentColor: Color = Color.LightGray,
) {
  Box(
    modifier = modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Row(verticalAlignment = Alignment.Bottom) {
      list.forEachIndexed { index, item ->
        Box(
          modifier = Modifier
            .clickable { onItemClick(index) }
            .padding(horizontal = 10.dp)
            .height(45.dp),
          contentAlignment = Alignment.Center
        ) {
          val selected = selectedIndex == index
          Text(
            text = mapToString(item),
            color = if (selected) selectedContentColor else unselectedContentColor,
          )
        }
      }
    }
  }
}
