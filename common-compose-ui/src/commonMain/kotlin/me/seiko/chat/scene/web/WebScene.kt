package me.seiko.chat.scene.web

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.seiko.compose.material.CustomIcon
import me.seiko.compose.material.CustomTopAppBar
import me.seiko.compose.web.WebView
import me.seiko.jetpack.LocalNavController

@Composable
fun WebScene(url: String) {
  val navController = LocalNavController.current

  Scaffold(
    topBar = {
      CustomTopAppBar(
        title = {},
        navigationIcon = {
          CustomIcon(
            image = Icons.Filled.ArrowBack,
            modifier = Modifier.clickable { navController.pop() }
          )
        }
      )
    }
  ) {
    WebView(url)
  }
}
