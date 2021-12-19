package me.seiko.compose.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import me.seiko.util.Logger

@Composable
actual fun WebView(url: String, modifier: Modifier) {
  val context = LocalContext.current
  val webView = remember(url) { AndroidCustomWebView(context, url) }
  WebView(webView, modifier)
}

class AndroidCustomWebView(
  private val context: Context,
  private var linkUrl: String,
) : CustomWebView {

  private val webView = WebView(context)

  override var onLoading: (Boolean) -> Unit = {}
  override var onProgress: (Float) -> Unit = {}

  @SuppressLint("SetJavaScriptEnabled")
  private fun setWebSettings() {
    val webSettings = webView.settings
    // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
    webSettings.javaScriptEnabled = true
    // 设置自适应屏幕，两者合用
    webSettings.useWideViewPort = true // 将图片调整到适合webview的大小
    webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
    // 缩放操作
    webSettings.setSupportZoom(true) // 支持缩放，默认为true。是下面那个的前提。
    webSettings.builtInZoomControls = true // 设置内置的缩放控件。若为false，则该WebView不可缩放
    webSettings.displayZoomControls = false // 隐藏原生的缩放控件

    // 其他细节操作
    webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 关闭webview中缓存
    webSettings.allowFileAccess = true // 设置可以访问文件
    webSettings.javaScriptCanOpenWindowsAutomatically = true // 支持通过JS打开新窗口
    webSettings.loadsImagesAutomatically = true // 支持自动加载图片
    webSettings.defaultTextEncodingName = "UTF-8" // 设置编码格式
  }

  private fun setupWebClient() {
    webView.webViewClient = AppWebViewClient()
    webView.webChromeClient = AppWebViewChromeClient()
  }

  private fun onCreate() {
    setWebSettings()
    setupWebClient()
    webView.loadUrl(linkUrl)
  }

  override fun onResume() {
    webView.onResume()
  }

  override fun onPause() {
    webView.onPause()
  }

  override fun onDestroy() {
    webView.destroy()
  }

  override fun goBack(): Boolean {
    if (webView.canGoBack()) {
      webView.goBack()
      return true
    }
    return false
  }

  @Composable
  override fun Content() {
    AndroidView(
      factory = {
        webView.apply {
          layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
          )
          onCreate()
        }
      },
      update = {}
    )
  }

  private inner class AppWebViewChromeClient : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
      super.onProgressChanged(view, newProgress)
      onProgress(newProgress / 100f)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
      super.onReceivedTitle(view, title)
    }
  }

  private inner class AppWebViewClient : WebViewClient() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(
      view: WebView?,
      request: WebResourceRequest?
    ): Boolean {
      val url = request?.url.toString()

      // 打开外部应用
      if (!url.startsWith("http")) {
        if (
          // 掘金
          url.startsWith("snssdk2606")
        ) {
          try {
            val intent = Intent(Intent.ACTION_VIEW, request!!.url)
            context.startActivity(intent)
          } catch (e: Exception) {
            Logger.w(tag = "WebView", e) { "open $url error:" }
          }
        }
        return true
      }

      linkUrl = url
      return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
      linkUrl = request?.url?.toString() ?: "NullUrlString"
      return super.shouldInterceptRequest(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
      onLoading(true)
      super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
      onLoading(false)
      super.onPageFinished(view, url)
    }
  }
}
