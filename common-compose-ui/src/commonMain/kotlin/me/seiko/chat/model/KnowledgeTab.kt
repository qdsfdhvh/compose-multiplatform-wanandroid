package me.seiko.chat.model

import me.seiko.chat.scene.Routes

enum class KnowledgeTab(
  val title: String,
  val route: String,
) {
  Course(
    title = "体系",
    route = Routes.Course,
  ),
  Navi(
    title = "导航",
    route = Routes.Navi,
  )
}
