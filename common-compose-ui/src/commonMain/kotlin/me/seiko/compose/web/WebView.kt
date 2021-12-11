package me.seiko.compose.web

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import me.seiko.compose.component.BackHandler
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver

@Composable
expect fun WebView(
  url: String,
  onLoading: (Boolean) -> Unit = {},
  onProgress: (Float) -> Unit = {},
  modifier: Modifier = Modifier
)

@Composable
fun WebView(
  webView: CustomWebView,
  modifier: Modifier = Modifier
) {
  val owner = checkNotNull(LocalLifecycleOwner.current) {
    "WebView requires a lifecycleOwner to be provided via LocalLifecycleOwner"
  }

  DisposableEffect(webView) {
    val lifecycleObserver = object : LifecycleObserver {
      override fun onStateChanged(state: Lifecycle.State) {
        when (state) {
          Lifecycle.State.Active -> {
            webView.onResume()
          }
          Lifecycle.State.InActive -> {
            webView.onPause()
          }
          else -> {}
        }
      }
    }
    owner.lifecycle.addObserver(lifecycleObserver)
    onDispose {
      owner.lifecycle.removeObserver(lifecycleObserver)
      webView.onDestroy()
    }
  }

  Box(modifier) {
    webView.Content()
  }

  BackHandler {
    webView.goBack()
  }
}

interface CustomWebView {
  var onLoading: (Boolean) -> Unit
  var onProgress: (Float) -> Unit

  fun onResume()
  fun onPause()
  fun onDestroy()
  fun goBack(): Boolean

  @Composable
  fun Content()
}
