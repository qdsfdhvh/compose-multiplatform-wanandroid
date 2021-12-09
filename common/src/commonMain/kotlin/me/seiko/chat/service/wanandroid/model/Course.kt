package me.seiko.chat.service.wanandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
  val children: List<Course>,
  val courseId: Int,
  val id: Int,
  val name: String,
  val order: Int,
  val parentChapterId: Int,
  val userControlSetTop: Boolean,
  val visible: Int,
)
