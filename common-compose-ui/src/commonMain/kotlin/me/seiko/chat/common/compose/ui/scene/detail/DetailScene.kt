package me.seiko.chat.common.compose.ui.scene.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.compose.ui.Routes
import me.seiko.chat.common.compose.ui.theme.AppScene
import me.seiko.jetpack.LocalNavController

@Composable
fun DetailScene(id: Int) {
  val navController = LocalNavController.current

  AppScene {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("详情") },
          navigationIcon = { Icon(Icons.Filled.ArrowBack, null) }
        )
      }
    ) {
      Box(Modifier.fillMaxSize(), Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("Detail $id")
          Button(onClick = { navController.navigate(Routes.Detail(id + 1)) }) {
            Text("go to detail")
          }
        }
      }
    }
  }
}
