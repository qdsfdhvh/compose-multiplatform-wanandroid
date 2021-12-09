package me.seiko.chat.model.ui

data class UiCourse(
  val children: List<UiCourse>,
  val id: Int,
  val name: String,
  // val url: String,
)
