package me.seiko.chat.common.compose.ui.scene.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.compose.ui.Routes
import me.seiko.compose.material.CustomTopAppBar
import me.seiko.jetpack.LocalNavController

@Composable
fun DetailScene(id: Int) {
  val navController = LocalNavController.current

  Scaffold(
    topBar = {
      CustomTopAppBar(
        title = { Text("Detail") },
        navigationIcon = {
          Icon(
            Icons.Filled.ArrowBack,
            null,
            Modifier.clickable { navController.pop() }
          )
        }
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
