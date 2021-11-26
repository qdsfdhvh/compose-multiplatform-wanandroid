package me.seiko.chat.common.compose.ui.scene.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.chat.common.compose.ui.theme.AppScene
import me.seiko.compose.pager.HorizontalPager
import me.seiko.compose.pager.rememberPagerState
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.compose.SceneContent

@Composable
fun HomeScene() {
  val navController = LocalNavController.current

  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState()

  val menus = remember { HomeMenus.values() }

  AppScene {
    Scaffold(
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
      }
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
  selectIndex: Int,
  onItemClick: (Int) -> Unit
) {
  BottomNavigation {
    items.forEachIndexed { index, item ->
      BottomNavigationItem(
        selected = selectIndex == index,
        icon = { Icon(item.icon, contentDescription = item.name) },
        onClick = { onItemClick(index) }
      )
    }
  }
}
