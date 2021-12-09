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
import me.seiko.chat.model.ui.UiCourse
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.jetpack.viewmodel.viewModelScope

@Composable
fun CourseScene() {
  val viewModel: CourseViewModel = getViewModel()
  val list by viewModel.list.collectAsState()
  LazyColumn(Modifier.fillMaxSize()) {
    items(list) { item ->
      Text(item.name)
    }
  }
}

class CourseViewModel(service: WanAndroidService) : BaseViewModel() {
  val list = flow {
    val list = service.getCourseList()
    emit(
      list.map { course ->
        UiCourse(
          name = course.name,
        )
      }
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
