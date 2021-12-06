package me.seiko.chat.scene.home.bottom

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.model.ui.UiBanner
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.jetpack.viewmodel.viewModelScope

class TimeLineViewModel(service: WanAndroidService) : BaseViewModel() {

  val banner = flow {
    val list = service.getHomeBanner().map { banner ->
      UiBanner(
        id = banner.id,
        imagePath = banner.imagePath,
      )
    }
    emit(list)
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val pager = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
    TimeLineDataSource(service, PAGE_SIZE)
  }.flow.cachedIn(viewModelScope)

  companion object {
    private const val PAGE_SIZE = 10
  }
}
