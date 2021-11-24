package me.seiko.chat.common.compose.ui.scene.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import me.seiko.chat.common.compose.ui.dialog.CustomDialog
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.chat.common.compose.ui.scene.me.MeScene
import me.seiko.chat.common.compose.ui.scene.mention.MentionScene
import me.seiko.chat.common.compose.ui.scene.search.SearchScene
import me.seiko.chat.common.compose.ui.scene.timeline.TimelineScene
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.compose.NavHost
import me.seiko.jetpack.navigation2.compose.collectBackStackEntryAsState
import me.seiko.jetpack.navigation2.compose.dialog
import me.seiko.jetpack.navigation2.compose.scene

@Composable
fun HomeScene() {
  val navController = LocalNavController.current
  val backstack by navController.collectBackStackEntryAsState()

  val menus = remember { HomeMenus.values() }

  Scaffold(
    topBar = {
      HomeTopBar()
    },
    bottomBar = {
      HomeBottomBar(
        items = menus,
        selectRoute = backstack?.scene?.route,
        onItemClick = { menu ->
          navController.navigate(menu.route) { popUpTo(menu.route) }
        }
      )
    }
  ) {
    NavHost(navController, initialRoute = HomeMenus.TimeLine.route) {
      scene(HomeMenus.TimeLine.route) { TimelineScene() }
      scene(HomeMenus.Mention.route) { MentionScene() }
      scene(HomeMenus.Search.route) { SearchScene() }
      scene(HomeMenus.Me.route) { MeScene() }

      dialog("/dialog") { CustomDialog() }
    }
  }
}

@Composable
fun HomeTopBar() {
  TopAppBar(
    title = { Text("Home") }
  )
}

@Composable
fun HomeBottomBar(
  items: Array<HomeMenus>,
  selectRoute: String?,
  onItemClick: (HomeMenus) -> Unit
) {
  BottomNavigation {
    items.forEach { item ->
      BottomNavigationItem(
        selected = selectRoute == item.route,
        icon = { Icon(item.icon, contentDescription = item.name) },
        onClick = { onItemClick(item) }
      )
    }
  }
}
