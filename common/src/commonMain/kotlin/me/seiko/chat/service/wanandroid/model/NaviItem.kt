package me.seiko.chat.service.wanandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class NaviItem(
  val articles: List<Article>,
  val cid: Int,
  val name: String,
)
