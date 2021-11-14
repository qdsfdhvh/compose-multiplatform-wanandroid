package me.seiko.chat.common.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.getPlatformName
import me.seiko.jetpack.LocalNavController
import me.seiko.jetpack.navigation.NavHost
import me.seiko.jetpack.navigation.rememberNavController

@Composable
fun App() {
  MaterialTheme {
    val navController = rememberNavController()

    CompositionLocalProvider(
      LocalNavController provides navController
    ) {
      NavHost(navController, initialRoute = "/home") {
        scene("/home") { HomeScreen() }
        scene("/detail") { DetailScreen() }
      }
    }
  }
}

@Composable
fun HomeScreen() {
  val navController = LocalNavController.current

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Home")
        }
      )
    }
  ) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Button(onClick = {
        navController.push("/detail")
      }) {
        Text("Hello, ${getPlatformName()}")
      }
    }
  }
}

@Composable
fun DetailScreen() {
  val navController = LocalNavController.current

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Detail")
        },
        navigationIcon = {
          IconButton(onClick = {
            navController.pop()
          }) {
            Icon(
              Icons.Default.ArrowBack,
              contentDescription = null
            )
          }
        }
      )
    }
  ) {
  }
}
