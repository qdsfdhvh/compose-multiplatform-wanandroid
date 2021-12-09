package me.seiko.chat.model.ui

data class UiNaviItem(
  val children: List<UiArticle>,
  val id: Int,
  val name: String,
) {
  data class UiArticle(
    val id: Int,
    val name: String,
    val url: String,
  )
}
