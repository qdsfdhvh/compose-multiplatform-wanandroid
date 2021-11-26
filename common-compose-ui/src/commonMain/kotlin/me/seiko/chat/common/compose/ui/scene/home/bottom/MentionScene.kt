package me.seiko.chat.common.compose.ui.scene.home.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.compose.ui.Routes
import me.seiko.jetpack.LocalNavController
import kotlin.random.Random

@Composable
fun MentionScene() {
  val navController = LocalNavController.current

  val id = rememberSaveable { Random.nextInt(100) }

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Column {
      Text("MentionScene $id")
      Button(onClick = { navController.navigate(Routes.Detail(id + 1)) }) {
        Text("go to detail")
      }
    }
  }
}
