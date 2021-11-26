package me.seiko.chat.common.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.seiko.chat.common.compose.ui.dialog.CustomDialog
import me.seiko.chat.common.compose.ui.scene.detail.DetailScene
import me.seiko.chat.common.compose.ui.scene.home.HomeScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.MeScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.MentionScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.SearchScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.TimelineScene
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

  object Detail : IRoute {
    override val route = "detail"
    operator fun invoke(id: Int) = "$route?id=$id"
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

      dialog(Routes.Dialog) { CustomDialog() }
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun NavGraphBuilder.scene(
  route: IRoute,
  noinline content: @Composable (NavBackStackEntry) -> Unit
) = scene(route.route, content)

private interface IRoute {
  val route: String
}
