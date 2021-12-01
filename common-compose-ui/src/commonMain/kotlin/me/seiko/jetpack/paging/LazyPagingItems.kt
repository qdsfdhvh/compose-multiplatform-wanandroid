package me.seiko.jetpack.paging

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.CombinedLoadStates
import androidx.paging.DifferCallback
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class LazyPagingItems<T : Any> internal constructor(
  private val flow: Flow<PagingData<T>>
) {
  private val mainDispatcher = Dispatchers.Main

  var itemSnapshotList by mutableStateOf(
    ItemSnapshotList<T>(0, 0, emptyList())
  )
    private set

  val itemCount: Int get() = itemSnapshotList.size

  private val differCallback: DifferCallback = object : DifferCallback {
    override fun onChanged(position: Int, count: Int) {
      if (count > 0) {
        updateItemSnapshotList()
      }
    }

    override fun onInserted(position: Int, count: Int) {
      if (count > 0) {
        updateItemSnapshotList()
      }
    }

    override fun onRemoved(position: Int, count: Int) {
      if (count > 0) {
        updateItemSnapshotList()
      }
    }
  }

  private val pagingDataDiffer = object : PagingDataDiffer<T>(
    differCallback = differCallback,
    mainDispatcher = mainDispatcher
  ) {
    override suspend fun presentNewList(
      previousList: NullPaddedList<T>,
      newList: NullPaddedList<T>,
      lastAccessedIndex: Int,
      onListPresentable: () -> Unit
    ): Int? {
      onListPresentable()
      updateItemSnapshotList()
      return null
    }
  }

  private fun updateItemSnapshotList() {
    itemSnapshotList = pagingDataDiffer.snapshot()
  }

  operator fun get(index: Int): T? {
    pagingDataDiffer[index] // this registers the value load
    return itemSnapshotList[index]
  }

  fun peek(index: Int): T? {
    return itemSnapshotList[index]
  }

  fun retry() {
    pagingDataDiffer.retry()
  }

  fun refresh() {
    pagingDataDiffer.refresh()
  }

  var loadState: CombinedLoadStates by mutableStateOf(
    CombinedLoadStates(
      refresh = InitialLoadStates.refresh,
      prepend = InitialLoadStates.prepend,
      append = InitialLoadStates.append,
      source = InitialLoadStates
    )
  )
    private set

  internal suspend fun collectLoadState() {
    pagingDataDiffer.loadStateFlow.collect {
      loadState = it
    }
  }

  internal suspend fun collectPagingData() {
    flow.collectLatest {
      pagingDataDiffer.collectFrom(it)
    }
  }
}

private val IncompleteLoadState = LoadState.NotLoading(false)
private val InitialLoadStates = LoadStates(
  IncompleteLoadState,
  IncompleteLoadState,
  IncompleteLoadState
)

@Composable
fun <T : Any> Flow<PagingData<T>>.collectAsLazyPagingItems(): LazyPagingItems<T> {
  val lazyPagingItems = remember(this) { LazyPagingItems(this) }

  LaunchedEffect(lazyPagingItems) {
    lazyPagingItems.collectPagingData()
  }
  LaunchedEffect(lazyPagingItems) {
    lazyPagingItems.collectLoadState()
  }

  return lazyPagingItems
}

fun <T : Any> LazyListScope.items(
  items: LazyPagingItems<T>,
  key: ((item: T) -> Any)? = null,
  itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
  items(
    count = items.itemCount,
    key = if (key == null) null else { index ->
      val item = items.peek(index)
      if (item == null) {
        PagingPlaceholderKey(index)
      } else {
        key(item)
      }
    }
  ) { index ->
    itemContent(items[index])
  }
}

fun <T : Any> LazyListScope.itemsIndexed(
  items: LazyPagingItems<T>,
  key: ((index: Int, item: T) -> Any)? = null,
  itemContent: @Composable LazyItemScope.(index: Int, value: T?) -> Unit
) {
  items(
    count = items.itemCount,
    key = if (key == null) null else { index ->
      val item = items.peek(index)
      if (item == null) {
        PagingPlaceholderKey(index)
      } else {
        key(index, item)
      }
    }
  ) { index ->
    itemContent(index, items[index])
  }
}

@JvmInline
private value class PagingPlaceholderKey(private val index: Int)
