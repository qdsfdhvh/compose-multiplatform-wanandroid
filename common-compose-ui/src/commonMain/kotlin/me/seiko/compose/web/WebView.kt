package me.seiko.compose.web

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.seiko.compose.component.BackHandler
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver

@Composable
expect fun WebView(
  url: String,
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

  val isRefreshing = remember { mutableStateOf(false) }
  val progress = remember { mutableStateOf(0f) }

  DisposableEffect(webView) {
    webView.onLoading = { isRefreshing.value = it }
    webView.onProgress = { progress.value = it }

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

  Column(modifier) {
    if (isRefreshing.value) {
      LinearProgressIndicator(
        progress = progress.value,
        modifier = Modifier.fillMaxWidth(),
      )
    }
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
