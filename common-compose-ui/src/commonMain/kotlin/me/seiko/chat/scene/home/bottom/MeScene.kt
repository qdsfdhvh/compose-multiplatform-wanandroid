package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.model.ui.UiUser
import me.seiko.compose.component.NetworkImage
import me.seiko.compose.component.statusBarsPadding
import me.seiko.compose.material.CustomIcon
import me.seiko.compose.material.CustomListItem
import me.seiko.compose.simple.SpacerHeight
import me.seiko.jetpack.LocalNavController
import me.seiko.kmp.resource.stringResource

@Composable
fun MeScene() {
  val navController = LocalNavController.current

  val viewModel: MeViewModel = getViewModel()
  val state by viewModel.state.collectAsState()

  LazyColumn {
    item {
      UserInfo(state.user)
    }
    items(state.menus) { menu ->
      CustomListItem(
        modifier = Modifier.clickable { navController.navigate(menu.route) },
        icon = { CustomIcon(menu.icon(), tint = MaterialTheme.colors.primarySurface) },
        text = { Text(stringResource(menu.title)) },
        trailing = { CustomIcon(Icons.Filled.ChevronRight, tint = Color.LightGray) },
      )
    }
  }
}

@Composable
private fun UserInfo(user: UiUser) {
  Box(
    modifier = Modifier
      .background(MaterialTheme.colors.primary)
      .statusBarsPadding()
      .height(150.dp)
      .fillMaxWidth(),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      NetworkImage(
        data = user.logo,
        contentDescription = null,
        modifier = Modifier.clip(CircleShape).size(80.dp)
      )
      SpacerHeight(10.dp)
      Text(
        text = user.name,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onPrimary,
      )
    }
  }
}
