package me.seiko.chat.common.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.getPlatformName

@Composable
fun App() {
  MaterialTheme {
    HomeScreen()
  }
}

@Composable
fun HomeScreen() {
  Scaffold {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      var text by remember { mutableStateOf("Hello, World!") }

      Button(onClick = {
        text = "Hello, ${getPlatformName()}"
      }) {
        Text(text)
      }
    }
  }
}
