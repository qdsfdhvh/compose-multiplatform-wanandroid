package me.seiko.chat.scene.home.bottom.knowledge

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import me.seiko.chat.di.extension.getViewModel
import me.seiko.compose.component.TextTabRow
import me.seiko.compose.component.statusBarsPadding
import me.seiko.compose.pager.HorizontalPager
import me.seiko.compose.pager.rememberPagerState
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation2.compose.SceneContent
import me.seiko.kmp.systemuicontroller.SetSystemBarsColor

@Composable
fun KnowledgeScene() {
  SetSystemBarsColor(darkIcons = true)

  val navController = LocalNavController.current

  val pagerState = rememberPagerState()
  val scope = rememberCoroutineScope()

  val viewModel: KnowledgeViewModel = getViewModel()
  val tabs = viewModel.tabs

  Scaffold(
    topBar = {
      TextTabRow(
        selectedIndex = pagerState.currentPage,
        list = tabs,
        mapToString = { it.title },
        onItemClick = { index ->
          scope.launch {
            pagerState.animateScrollToPage(index)
          }
        },
        modifier = Modifier.statusBarsPadding(),
      )
    }
  ) {
    HorizontalPager(
      count = viewModel.tabs.size,
      state = pagerState,
    ) { index ->
      navController.SceneContent(tabs[index].route)
    }
  }
}
