package me.seiko.chat.common.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.seiko.chat.common.compose.ui.dialog.CustomDialog
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.chat.common.compose.ui.scene.detail.DetailScene
import me.seiko.chat.common.compose.ui.scene.home.HomeScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.MeScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.MentionScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.SearchScene
import me.seiko.chat.common.compose.ui.scene.home.bottom.TimelineScene
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.NavController
import me.seiko.jetpack.navigation2.compose.NavHost
import me.seiko.jetpack.navigation2.compose.dialog
import me.seiko.jetpack.navigation2.compose.rememberNavController
import me.seiko.jetpack.navigation2.compose.scene

@Composable
fun Route(navController: NavController = rememberNavController()) {
  CompositionLocalProvider(
    LocalNavController provides navController
  ) {
    NavHost(navController, initialRoute = "/home") {
      scene("/home") { HomeScene() }
      scene(HomeMenus.TimeLine.route) { TimelineScene() }
      scene(HomeMenus.Mention.route) { MentionScene() }
      scene(HomeMenus.Search.route) { SearchScene() }
      scene(HomeMenus.Me.route) { MeScene() }

      scene("/detail") { DetailScene(it.query("id", 0)) }

      dialog("/dialog") { CustomDialog() }
    }
  }
}
