package me.seiko.compose.web

import android.webkit.WebView
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import me.seiko.compose.component.BackHandler
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver

typealias AndroidWebView = WebView

@Composable
actual fun WebView(url: String, modifier: Modifier) {
  val owner = checkNotNull(LocalLifecycleOwner.current) {
    "WebView requires a lifecycleOwner to be provided via LocalLifecycleOwner"
  }

  var webViewController by remember { mutableStateOf<WebViewController?>(null) }
  var isRefreshing by remember { mutableStateOf(false) }
  var progress by remember { mutableStateOf(0f) }

  Column {
    if (isRefreshing) {
      LinearProgressIndicator(progress)
    }
    AndroidView(
      factory = { context ->
        WebView(context).apply {
          layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
          )

          val controller = WebViewController(
            context = context,
            linkUrl = url,
            webView = this,
            onProgress = { progress = it / 100f },
            onLoading = { isRefreshing = it }
          )
          controller.initSettings()

          val lifecycleObserver = object : LifecycleObserver {
            override fun onStateChanged(state: Lifecycle.State) {
              when (state) {
                Lifecycle.State.Active -> {
                  controller.onResume()
                }
                Lifecycle.State.InActive -> {
                  controller.onPause()
                }
                Lifecycle.State.Destroyed -> {
                  controller.onDestroy()
                  owner.lifecycle.removeObserver(this)
                }
                else -> {}
              }
            }
          }
          owner.lifecycle.addObserver(lifecycleObserver)

          controller.refresh()
          webViewController = controller
        }
      },
      update = {}
    )
  }

  BackHandler {
    webViewController?.goBack() ?: false
  }
}
