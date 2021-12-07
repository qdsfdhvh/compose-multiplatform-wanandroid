package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import me.seiko.chat.Routes
import me.seiko.chat.di.extension.getViewModel
import me.seiko.compose.component.Banner
import me.seiko.compose.component.NetworkImage
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
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
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

    items(pageData) { item ->
      if (item != null) {
        Text(
          text = item.title,
          modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colors.primary)
            .padding(10.dp)
            .clickable { navController.navigate(Routes.Detail(item.id)) }
        )
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
