package me.seiko.chat.common.compose.ui.scene.mention

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.seiko.chat.common.compose.ui.model.HomeMenus
import me.seiko.jetpack.LocalNavController
import kotlin.random.Random

@Composable
fun MentionScene() {
  val navController = LocalNavController.current

  val id = remember { Random.nextInt(100) }

  Box(Modifier.fillMaxSize(), Alignment.Center) {
    Column {
      Text("MentionScene $id")
      Button(onClick = { navController.navigate(HomeMenus.Mention.route) }) {
        Text("go to mention")
      }
    }
  }
}
