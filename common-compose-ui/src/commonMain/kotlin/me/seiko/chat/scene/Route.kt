package me.seiko.chat.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.seiko.chat.HTTP_REGEX
import me.seiko.chat.dialog.CustomDialog
import me.seiko.chat.scene.detail.DetailScene
import me.seiko.chat.scene.home.HomeScene
import me.seiko.chat.scene.home.bottom.MeScene
import me.seiko.chat.scene.home.bottom.MentionScene
import me.seiko.chat.scene.home.bottom.SearchScene
import me.seiko.chat.scene.home.bottom.TimelineScene
import me.seiko.chat.scene.user.UserScene
import me.seiko.chat.scene.web.WebScene
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.NavGraphBuilder
import me.seiko.jetpack.navigation2.compose.NavHost
import me.seiko.jetpack.navigation2.compose.dialog
import me.seiko.jetpack.navigation2.compose.rememberNavController
import me.seiko.jetpack.navigation2.compose.scene

object Routes {
  const val Home = "home"
  const val TimeLine = "timeline"
  const val Mention = "mention"
  const val Search = "search"
  const val Me = "me"

  object Detail : IRoute("detail") {
    operator fun invoke(id: Int) = "$route?id=$id"
  }

  object User : IRoute("user/{id}/{name}") {
    operator fun invoke(id: Int, name: String) = "user/$id/$name"
  }

  const val Dialog = "dialog"
}

@Composable
fun Route(navController: NavController = rememberNavController()) {
  CompositionLocalProvider(
    LocalNavController provides navController
  ) {
    NavHost(navController, initialRoute = Routes.Home) {
      scene(Routes.Home) { HomeScene() }
      scene(Routes.TimeLine) { TimelineScene() }
      scene(Routes.Mention) { MentionScene() }
      scene(Routes.Search) { SearchScene() }
      scene(Routes.Me) { MeScene() }
      scene(Routes.Detail) { DetailScene(it.query("id", 0)) }
      scene(Routes.User) { UserScene(it.param("id", 0), it.param("name", "")) }

      dialog(Routes.Dialog) { CustomDialog() }

      scene(HTTP_REGEX) { WebScene(it.route) }
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun NavGraphBuilder.scene(
  route: IRoute,
  noinline content: @Composable (NavBackStackEntry) -> Unit
) = scene(route.route, content)

abstract class IRoute(val route: String)
