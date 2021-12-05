package me.seiko.chat.android

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat
import me.seiko.chat.App
import me.seiko.compose.insets.ProvideWindowInsets
import me.seiko.jetpack.lifecycle.PreComposeActivity
import me.seiko.jetpack.lifecycle.setContent

class MainActivity : PreComposeActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT

    setContent {
      ProvideWindowInsets {
        App()
      }
    }
  }
}
