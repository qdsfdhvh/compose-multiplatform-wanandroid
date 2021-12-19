package me.seiko.chat.android

import android.graphics.Color
import android.os.Bundle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import me.seiko.chat.App
import me.seiko.compose.insets.ProvideWindowInsets
import me.seiko.jetpack.lifecycle.PreComposeActivity
import me.seiko.jetpack.lifecycle.setContent
import me.seiko.kmp.LocalResLoader
import me.seiko.kmp.resource.ResLoader

class MainActivity : PreComposeActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT

    setContent {
      ProvideWindowInsets {
        CompositionLocalProvider(
          LocalResLoader provides ResLoader(this)
        ) {
          App()
        }
      }
    }
  }
}
