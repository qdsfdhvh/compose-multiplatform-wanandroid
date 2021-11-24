package me.seiko.chat.common.compose.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.jetpack.viewmodel.getViewModel
import kotlin.random.Random

@Composable
fun CustomDialog() {
  val owner = LocalViewModelStoreOwner.current

  val viewModel = remember {
    owner!!.viewModelStore.getViewModel { CustomDialogViewModel() }
  }

  Box(
    modifier = Modifier
      .background(Color.Black.copy(alpha = 0.6f))
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Surface(
      modifier = Modifier.size(200.dp),
      color = MaterialTheme.colors.background,
      shape = MaterialTheme.shapes.medium
    ) {
      Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("This is Dialog ${viewModel.randomNum}")
      }
    }
  }
}

class CustomDialogViewModel : ViewModel() {

  val randomNum = Random.nextInt(10)

  override fun onCleared() {
    Napier.d({ "CustomDialogViewModel onCleared" })
  }
}