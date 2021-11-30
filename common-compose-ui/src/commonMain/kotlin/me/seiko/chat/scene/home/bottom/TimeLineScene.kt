package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.Routes
import me.seiko.jetpack.LocalNavController

@Composable
fun TimelineScene() {
  val navController = LocalNavController.current

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Column {
      Text("TimelineScene")
      Button(onClick = { navController.navigate(Routes.Dialog) }) {
        Text("show dialog")
      }
    }
  }
}
