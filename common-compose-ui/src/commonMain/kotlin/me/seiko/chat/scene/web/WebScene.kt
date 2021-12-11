package me.seiko.chat.scene.web

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.seiko.compose.component.ProgressIcon
import me.seiko.compose.component.statusBarsHeight
import me.seiko.compose.web.WebView
import me.seiko.jetpack.LocalNavController

@Composable
fun WebScene(url: String) {
  val navController = LocalNavController.current

  var isRefreshing by remember { mutableStateOf(false) }
  var progress by remember { mutableStateOf(0f) }


  Column {
    Spacer(
      modifier = Modifier
        .background(MaterialTheme.colors.background)
        .statusBarsHeight()
        .fillMaxWidth()
    )
    Box(Modifier) {
      WebView(
        url = url,
        onLoading = { isRefreshing = it },
        onProgress = { progress = it },
      )
      ProgressIcon(
        image = Icons.Filled.ArrowBack,
        progress = if (isRefreshing) progress else 0f,
        onClick = { navController.pop() },
        modifier = Modifier.padding(10.dp),
      )
    }
  }
}
