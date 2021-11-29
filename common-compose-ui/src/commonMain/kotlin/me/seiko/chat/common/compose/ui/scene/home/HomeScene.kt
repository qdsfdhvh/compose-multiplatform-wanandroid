package me.seiko.chat.common.compose.ui.scene.home

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.compose.component.BackHandler
import me.seiko.compose.component.Pager
import me.seiko.compose.component.rememberPagerState
import me.seiko.compose.material.CustomBottomNavigation
import me.seiko.compose.material.CustomTopAppBar
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.compose.SceneContent

@Composable
fun HomeScene() {
  val navController = LocalNavController.current

  val menus = remember { HomeMenus.values() }

  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState(menus.size)
  val scaffoldState = rememberScaffoldState()

  if (scaffoldState.drawerState.isOpen) {
    BackHandler {
      scope.launch {
        scaffoldState.drawerState.close()
      }
      true
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      HomeTopBar()
    },
    bottomBar = {
      HomeBottomBar(
        items = menus,
        selectIndex = pagerState.currentPage,
        onItemClick = { index ->
          scope.launch {
            pagerState.currentPage = index
          }
        }
      )
    },
    drawerContent = {
      HomeDrawer()
    },
  ) {
    Pager(
      state = pagerState,
    ) {
      navController.SceneContent(menus[page].route)
    }
  }
}

@Composable
fun HomeTopBar() {
  CustomTopAppBar(
    title = { Text("Home") },
  )
}

@Composable
fun HomeBottomBar(
  items: Array<HomeMenus>,
  selectIndex: Int,
  onItemClick: (Int) -> Unit
) {
  CustomBottomNavigation {
    items.forEachIndexed { index, item ->
      BottomNavigationItem(
        selected = selectIndex == index,
        icon = { Icon(item.icon, contentDescription = item.name) },
        onClick = { onItemClick(index) }
      )
    }
  }
}

@Composable
fun HomeDrawer() {
}
