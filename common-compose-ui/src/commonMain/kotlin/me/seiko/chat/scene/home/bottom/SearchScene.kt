package me.seiko.chat.scene.home.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.di.extension.getViewModel

@Composable
fun SearchScene() {
  val viewModel: SearchViewModel = getViewModel()

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Text("SearchScene ${viewModel.id}")
  }
}
