package me.seiko.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
actual fun WebView(
  url: String,
  onLoading: (Boolean) -> Unit,
  onProgress: (Float) -> Unit,
  modifier: Modifier
) {
  val webView = remember(url) {
    DesktopCustomWebViewFactory.create(url).apply {
      this.onLoading = onLoading
      this.onProgress = onProgress
    }
  }
  WebView(webView, modifier)
}

interface DesktopCustomWebViewFactory {
  fun create(startUrl: String): CustomWebView

  companion object : DesktopCustomWebViewFactory {
    private var current: DesktopCustomWebViewFactory? = null

    fun register(factory: DesktopCustomWebViewFactory) {
      current = factory
    }

    override fun create(startUrl: String): CustomWebView {
      return checkNotNull(current) {
        "Please register DesktopWebBrowserFactory"
      }.create(startUrl)
    }
  }
}
