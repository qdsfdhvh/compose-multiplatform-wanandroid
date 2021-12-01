package me.seiko.chat.scene.detail

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
import me.seiko.chat.Routes
import me.seiko.chat.di.extension.getViewModel
import me.seiko.compose.material.CustomTopAppBar
import me.seiko.jetpack.LocalNavController
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScene(id: Int) {
  val navController = LocalNavController.current

  val viewModel: DetailViewModel = getViewModel {
    parametersOf(id)
  }

  Scaffold(
    topBar = {
      CustomTopAppBar(
        title = { Text("Detail ${viewModel.key}") },
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
