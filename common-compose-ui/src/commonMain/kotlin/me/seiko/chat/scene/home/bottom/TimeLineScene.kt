package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.seiko.chat.Routes
import me.seiko.chat.di.extension.getViewModel
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.paging.collectAsLazyPagingItems
import me.seiko.jetpack.paging.items

@Composable
fun TimelineScene() {
  val navController = LocalNavController.current

  val viewModel: TimeLineViewModel = getViewModel()
  val pageData = viewModel.pager.collectAsLazyPagingItems()

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
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
  }
}
