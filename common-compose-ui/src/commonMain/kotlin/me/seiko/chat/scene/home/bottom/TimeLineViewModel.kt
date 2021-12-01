package me.seiko.chat.scene.home.bottom

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import me.seiko.chat.base.BaseViewModel
import me.seiko.jetpack.viewmodel.viewModelScope
import kotlin.random.Random

class TimeLineViewModel : BaseViewModel() {
  val id = Random.nextInt(100)

  val pager = Pager(PagingConfig(pageSize = 10)) {
    TimeLineDataSource()
  }.flow.cachedIn(viewModelScope)
}
