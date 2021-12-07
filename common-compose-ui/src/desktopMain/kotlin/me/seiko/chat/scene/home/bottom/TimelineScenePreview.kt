package me.seiko.chat.scene.home.bottom

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import me.seiko.chat.model.ui.UiTimeLine

@Preview
@Composable
fun TimeLineItemPreview() {
  TimeLineItem(
    item = UiTimeLine(
      id = 0,
      title = "给高级Android工程师的进阶手册",
      author = "仍物线",
      time = "2021-11-11 10:10:00",
      tip = "干活资源·课程推荐",
      url = "",
      isStar = true,
    ),
    onItemClick = {}
  )
}
