package me.seiko.chat.scene.home.bottom

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.service.wanandroid.WanAndroidService
import me.seiko.jetpack.viewmodel.viewModelScope

class TimeLineViewModel(service: WanAndroidService) : BaseViewModel() {

  val pager = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
    TimeLineDataSource(service, PAGE_SIZE)
  }.flow.cachedIn(viewModelScope)

  companion object {
    private const val PAGE_SIZE = 10
  }
}
