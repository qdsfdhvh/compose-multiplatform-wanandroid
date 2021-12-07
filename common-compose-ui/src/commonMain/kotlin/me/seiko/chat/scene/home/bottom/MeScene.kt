package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.di.extension.getViewModel
import me.seiko.chat.scene.Routes
import me.seiko.jetpack.LocalNavController

@Composable
fun MeScene() {
  val navController = LocalNavController.current

  val viewModel: MeViewModel = getViewModel()

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Column {
      Text("MeScene ${viewModel.id}")
      Button(onClick = { navController.navigate(Routes.User(viewModel.id + 1, "Lulu")) }) {
        Text("go to user")
      }
    }
  }
}
