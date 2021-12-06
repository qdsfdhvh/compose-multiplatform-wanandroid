package me.seiko.chat.service.wanandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class Banner(
  val id: Int,
  val desc: String,
  val imagePath: String,
  val isVisible: Int,
  val order: Int,
  val title: String,
  val type: Int,
  val url: String,
)
