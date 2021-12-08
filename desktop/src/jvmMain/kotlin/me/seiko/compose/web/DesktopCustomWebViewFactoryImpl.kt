package me.seiko.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import me.seiko.compose.component.JavaFXPanel
import me.seiko.jetpack.LocalComposeWindow

object DesktopCustomWebViewFactoryImpl : DesktopCustomWebViewFactory {
  override fun create(startUrl: String): CustomWebView {
    return DesktopCustomWebView(startUrl)
  }
}

private class DesktopCustomWebView(
  private var linkUrl: String,
) : CustomWebView {

  override var onLoading: (Boolean) -> Unit = {}
  override var onProgress: (Float) -> Unit = {}

  private fun onCreate(webView: WebView) {
    val engine = webView.engine
    engine.loadWorker.stateProperty().addListener { _, _, state ->
      when (state) {
        Worker.State.RUNNING -> onLoading(true)
        Worker.State.SUCCEEDED,
        Worker.State.FAILED -> onLoading(false)
        else -> {}
      }
    }
    engine.loadWorker.progressProperty().addListener { _, _, progress ->
      onProgress(progress.toFloat())
    }

    engine.load(linkUrl)
  }

  override fun onResume() {
  }

  override fun onPause() {
  }

  override fun onDestroy() {
  }

  override fun goBack(): Boolean {
    return false
  }

  @Composable
  override fun Content() {
    val container = LocalComposeWindow.current
    val webPanel = remember { JFXPanel() }

    JavaFXPanel(container, webPanel, onCreate = {
      // https://stackoverflow.com/questions/29302837/javafx-platform-runlater-never-running
      Platform.setImplicitExit(false)

      Platform.runLater {
        val webView = WebView()
        webPanel.scene = Scene(webView)

        onCreate(webView)
      }
    })
  }
}
