package me.seiko.chat.scene.home.bottom.knowledge.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.model.ui.UiNaviItem
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.compose.component.FlowRow
import me.seiko.compose.simple.SpacerHeight
import me.seiko.compose.simple.TextSubTitle2
import me.seiko.compose.simple.TextTag
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.viewmodel.viewModelScope

@Composable
fun NaviScene() {
  val navController = LocalNavController.current

  val viewModel: NaviViewModel = getViewModel()
  val list by viewModel.list.collectAsState()

  LazyColumn(Modifier.fillMaxSize()) {
    items(list) { item ->
      NaviItem(item) { article ->
        navController.navigate(article.url)
      }
    }
  }
}

@Composable
fun NaviItem(item: UiNaviItem, onItemClick: (UiNaviItem.UiArticle) -> Unit) {
  Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
    TextSubTitle2(item.name)
    SpacerHeight(6.dp)
    FlowRow(contentPadding = 6.dp) {
      item.children.forEach { article ->
        TextTag(article.name, Modifier.clickable { onItemClick(article) })
      }
    }
  }
}

class NaviViewModel(service: WanAndroidService) : BaseViewModel() {
  val list = flow {
    val list = service.getNaviList()
    emit(
      list.map { item ->
        UiNaviItem(
          children = item.articles.map { article ->
            UiNaviItem.UiArticle(
              id = article.id,
              name = article.title,
              url = article.link.trim(),
            )
          },
          id = item.cid,
          name = item.name,
        )
      }
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
