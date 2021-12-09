package me.seiko.chat.scene.home.bottom.knowledge.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.model.ui.UiNaviItem
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.jetpack.viewmodel.viewModelScope

@Composable
fun NaviScene() {
  val viewModel: NaviViewModel = getViewModel()
  val list by viewModel.list.collectAsState()
  LazyColumn(Modifier.fillMaxSize()) {
    items(list) { item ->
      Text(item.name)
    }
  }
}

class NaviViewModel(service: WanAndroidService) : BaseViewModel() {
  val list = flow {
    val list = service.getNaviList()
    emit(
      list.map { item ->
        UiNaviItem(
          name = item.name,
        )
      }
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
