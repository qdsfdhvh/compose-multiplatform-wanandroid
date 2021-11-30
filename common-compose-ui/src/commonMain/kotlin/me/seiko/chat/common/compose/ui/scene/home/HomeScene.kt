package me.seiko.chat.common.compose.ui.scene.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.chat.common.compose.ui.model.ui.UiUser
import me.seiko.compose.component.BackHandler
import me.seiko.compose.component.NetworkImage
import me.seiko.compose.component.Pager
import me.seiko.compose.component.rememberPagerState
import me.seiko.compose.component.statusBarsPadding
import me.seiko.compose.material.CustomBottomNavigation
import me.seiko.compose.material.CustomBottomNavigationItem
import me.seiko.compose.material.CustomListItem
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

  val user = remember {
    UiUser(
      name = "SeikoDes",
      logo = "https://z3.ax1x.com/2021/11/28/ouJxVx.jpg"
    )
  }

  if (scaffoldState.drawerState.isOpen) {
    BackHandler(scope) {
      scaffoldState.drawerState.close()
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
      HomeDrawer(user = user)
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
      CustomBottomNavigationItem(
        selected = selectIndex == index,
        icon = { Icon(item.icon, contentDescription = item.name) },
        onClick = { onItemClick(index) }
      )
    }
  }
}

@Composable
fun HomeDrawer(user: UiUser) {
  Column {
    HomeDrawerUserHeader(user)
    Divider()
  }
}

@Composable
fun HomeDrawerUserHeader(user: UiUser) {
  CustomListItem(
    modifier = Modifier
      .background(MaterialTheme.colors.primarySurface)
      .statusBarsPadding(),
    icon = {
      NetworkImage(
        data = user.logo,
        modifier = Modifier.clip(CircleShape).size(40.dp),
        contentDescription = null,
        contentScale = ContentScale.Crop,
      )
    },
    text = {
      Text(user.name)
    },
    secondaryText = {
      Text(user.name)
    },
    trailing = {
      Icon(
        Icons.Filled.ArrowDropDown,
        contentDescription = null
      )
    }
  )
}
