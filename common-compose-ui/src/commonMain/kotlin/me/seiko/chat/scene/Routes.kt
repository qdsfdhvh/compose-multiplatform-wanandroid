package me.seiko.chat.scene

import com.seiko.processor.route.AppRoute

@AppRoute
expect object Routes {
  val Home: String
  val TimeLine: String
  val Knowledge: String
  val Search: String
  val Me: String
  val Course: String
  val Navi: String

  object Detail : IRoute {
    operator fun invoke(id: Int?): String
  }

  object User : IRoute {
    operator fun invoke(id: Int, name: String): String
  }

  val Dialog: String
}

//object Routes {
//   const val Home = "home"
//   const val TimeLine = "timeline"
//   const val Knowledge = "Knowledge"
//   const val Search = "search"
//   const val Me = "me"
//   const val Course = "course"
//   const val Navi = "navi"
//
//   object Detail : IRoute("detail") {
//     operator fun invoke(id: Int) = "$route?id=$id"
//   }
//
//   object User : IRoute("user/{id}/{name}") {
//     operator fun invoke(id: Int, name: String) = "user/$id/$name"
//   }
//
//   const val Dialog = "dialog"
// }