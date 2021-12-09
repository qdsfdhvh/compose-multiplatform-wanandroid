package me.seiko.chat.scene.home.bottom.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.model.ui.UiBanner
import me.seiko.chat.model.ui.UiTimeLine
import me.seiko.compose.component.Banner
import me.seiko.compose.component.NetworkImage
import me.seiko.compose.component.statusBarsHeightDp
import me.seiko.compose.material.CustomIcon
import me.seiko.compose.simple.RowSpaceBetween
import me.seiko.compose.simple.SpacerHeight
import me.seiko.compose.simple.TextCaption
import me.seiko.compose.simple.TextSubTitle1
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.paging.collectAsLazyPagingItems
import me.seiko.jetpack.paging.items
import me.seiko.kmp.systemuicontroller.SetSystemBarsColor
import me.seiko.util.Logger

@Composable
fun TimelineScene() {
  SetSystemBarsColor(darkIcons = false)

  val navController = LocalNavController.current

  val viewModel: TimeLineViewModel = getViewModel()
  val banner = viewModel.banner.collectAsState()
  val pageData = viewModel.pager.collectAsLazyPagingItems()

  val statusBarsHeight = statusBarsHeightDp
  val bannerHeightPx = with(LocalDensity.current) { 200.dp.toPx() }

  val statusBarAlpha = rememberSaveable { mutableStateOf(0f) }

  val nestedScrollConnection = remember {
    object : NestedScrollConnection {
      override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val newOffset = viewModel.statusBarOffset + available.y
        viewModel.statusBarOffset = newOffset.coerceAtMost(0f)
        if (viewModel.statusBarOffset >= -bannerHeightPx) {
          statusBarAlpha.value = -viewModel.statusBarOffset / bannerHeightPx
        }
        return Offset.Zero
      }
    }
  }

  Box {
    LazyColumn(
      state = if (pageData.itemCount > 0) viewModel.listState else viewModel.listStateZero,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .nestedScroll(nestedScrollConnection)
        .fillMaxSize(),
    ) {
      if (pageData.itemCount > 0) {
        item {
          TimeLineBanner(banner.value)
        }
      }

      items(pageData) { item ->
        TimeLineItem(item!!) {
          navController.navigate(item.url)
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

    if (statusBarAlpha.value > 0) {
      Spacer(
        Modifier
          .alpha(statusBarAlpha.value)
          .background(MaterialTheme.colors.primary)
          .height(statusBarsHeight)
          .fillMaxWidth()
      )
    }
  }
}

@Composable
fun TimeLineBanner(banner: List<UiBanner>) {
  if (banner.isEmpty()) return
  Banner(
    list = banner,
    loopDuration = 2500,
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
