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
import me.seiko.compose.material.CustomBottomNavigation
import me.seiko.compose.material.CustomTopAppBar
import me.seiko.compose.pager.HorizontalPager
import me.seiko.compose.pager.rememberPagerState
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.compose.SceneContent

@Composable
fun HomeScene() {
  val navController = LocalNavController.current

  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState()
  val scaffoldState = rememberScaffoldState()

  val menus = remember { HomeMenus.values() }

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
            pagerState.scrollToPage(index)
          }
        }
      )
    },
    drawerContent = {
      HomeDrawer()
    },
  ) {
    HorizontalPager(
      count = menus.size,
      state = pagerState,
      key = { index -> menus[index].route }
    ) { index ->
      navController.SceneContent(menus[index].route)
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
