package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.Routes
import me.seiko.jetpack.LocalNavController
import kotlin.random.Random

@Composable
fun MeScene() {
  val navController = LocalNavController.current

  val id = rememberSaveable { Random.nextInt(100) }

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Column {
      Text("MeScene $id")
      Button(onClick = { navController.navigate(Routes.User(id + 1, "Lulu")) }) {
        Text("go to user")
      }
    }
  }
}
