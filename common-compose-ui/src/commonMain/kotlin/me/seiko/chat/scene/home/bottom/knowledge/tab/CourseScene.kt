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
import me.seiko.chat.model.ui.UiCourse
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.compose.component.FlowRow
import me.seiko.compose.simple.SpacerHeight
import me.seiko.compose.simple.TextSubTitle2
import me.seiko.compose.simple.TextTag
import me.seiko.jetpack.viewmodel.viewModelScope

@Composable
fun CourseScene() {
  val viewModel: CourseViewModel = getViewModel()
  val list by viewModel.list.collectAsState()
  LazyColumn(Modifier.fillMaxSize()) {
    items(list) { item ->
      CourseItem(item) {
      }
    }
  }
}

@Composable
private fun CourseItem(item: UiCourse, onItemClick: (UiCourse) -> Unit) {
  Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
    TextSubTitle2(item.name)
    SpacerHeight(6.dp)
    FlowRow(contentPadding = 6.dp) {
      item.children.forEach { child ->
        TextTag(child.name, Modifier.clickable { onItemClick(child) })
      }
    }
  }
}

class CourseViewModel(service: WanAndroidService) : BaseViewModel() {
  val list = flow {
    val list = service.getCourseList().sortedBy { it.order }
    emit(
      list.map { course ->
        UiCourse(
          children = course.children.map {
            UiCourse(
              children = emptyList(),
              id = it.id,
              name = it.name,
            )
          },
          id = course.id,
          name = course.name,
        )
      }
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
