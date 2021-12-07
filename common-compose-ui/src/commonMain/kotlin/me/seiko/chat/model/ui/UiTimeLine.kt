package me.seiko.chat.model.ui

data class UiTimeLine(
  val id: Int,
  val title: String,
  val author: String,
  val time: String,
  val tip: String,
  val isStar: Boolean
)
