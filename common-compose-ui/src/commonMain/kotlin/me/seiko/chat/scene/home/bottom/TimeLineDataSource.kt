package me.seiko.chat.scene.home.bottom

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.seiko.chat.model.ui.UiTimeLine

class TimeLineDataSource : PagingSource<Int, UiTimeLine>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiTimeLine> {
    val page = params.key ?: 0
    return LoadResult.Page(
      data = List(10) {
        UiTimeLine(
          id = page * 10 + it,
          title = "page=$page id=${page * 10 + it}"
        )
      },
      prevKey = if (page > 0) page - 1 else null,
      nextKey = if (page < 10) page + 1 else null
    )
  }

  override fun getRefreshKey(state: PagingState<Int, UiTimeLine>): Int? = null
}
