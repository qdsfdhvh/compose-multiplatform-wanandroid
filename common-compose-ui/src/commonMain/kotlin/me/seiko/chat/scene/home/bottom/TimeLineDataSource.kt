package me.seiko.chat.scene.home.bottom

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.seiko.chat.model.ui.UiTimeLine
import me.seiko.chat.service.wanandroid.WanAndroidService

class TimeLineDataSource(
  private val service: WanAndroidService,
  private val pageSize: Int,
) : PagingSource<Int, UiTimeLine>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiTimeLine> {
    val page = params.key ?: 0
    return try {
      LoadResult.Page(
        data = service.getIndexList(page, pageSize).map { article ->
          UiTimeLine(
            id = article.id,
            title = article.title,
            author = article.author.ifEmpty { article.shareUser },
            time = article.niceDate,
            tip = listOf(article.chapterName, article.superChapterName).joinToString("·"),
            isStar = false
          )
        },
        prevKey = if (page > 0) page - 1 else null,
        nextKey = page + 1
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, UiTimeLine>): Int? = null
}
