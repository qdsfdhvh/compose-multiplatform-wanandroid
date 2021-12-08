package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.model.ui.UiTimeLine
import me.seiko.compose.component.Banner
import me.seiko.compose.component.NetworkImage
import me.seiko.compose.material.CustomIcon
import me.seiko.compose.simple.RowSpaceBetween
import me.seiko.compose.simple.SpacerHeight
import me.seiko.compose.simple.TextCaption
import me.seiko.compose.simple.TextSubTitle1
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.paging.collectAsLazyPagingItems
import me.seiko.jetpack.paging.items
import me.seiko.util.Logger

@Composable
fun TimelineScene() {
  val navController = LocalNavController.current

  val viewModel: TimeLineViewModel = getViewModel()
  val banner by viewModel.banner.collectAsState()
  val pageData = viewModel.pager.collectAsLazyPagingItems()

  val scope = rememberCoroutineScope()

  LazyColumn(
    state = if (pageData.itemCount > 0) viewModel.listState else viewModel.listStateZero,
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    if (pageData.itemCount > 0) {
      item {
        Banner(
          list = banner,
          coroutineScope = scope,
          loopDuration = 2000,
          modifier = Modifier.height(200.dp)
        ) { item, _ ->
          NetworkImage(
            data = item.imagePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }

    items(pageData) { item ->
      if (item != null) {
        TimeLineItem(item) {
          navController.navigate(item.url)
        }
      }
    }

    pageData.loadState.append.let {
      when (it) {
        is LoadState.Error -> {
          Logger.w(tag = "Timeline", throwable = it.error) { "Home Timeline error:" }
        }
        else -> {}
      }
    }
  }
}

@Composable
fun TimeLineItem(
  item: UiTimeLine,
  onItemClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .padding(10.dp)
      .fillMaxWidth()
      .clickable { onItemClick() }
  ) {
    RowSpaceBetween {
      TextCaption(item.author)
      TextCaption(item.time)
    }
    SpacerHeight(2.dp)
    TextSubTitle1(item.title)
    SpacerHeight(2.dp)
    RowSpaceBetween {
      TextCaption(item.tip)
      val image = if (item.isStar) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
      CustomIcon(image, Modifier.size(16.dp))
    }
  }
}
