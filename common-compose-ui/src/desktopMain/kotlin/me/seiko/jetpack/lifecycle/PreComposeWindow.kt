package me.seiko.jetpack.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import me.seiko.jetpack.LocalBackDispatcherOwner
import me.seiko.jetpack.LocalComposeWindow
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.dispatcher.BackDispatcher
import me.seiko.jetpack.dispatcher.BackDispatcherOwner
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner

@Composable
fun PreComposeWindow(
  title: String = "JetpackDesktopWindow",
  state: WindowState = WindowState(
    width = 800.dp,
    height = 600.dp,
  ),
  onCloseRequest: (() -> Unit) = {},
  content: @Composable () -> Unit = { }
) {
  Window(
    title = title,
    state = state,
    onCloseRequest = onCloseRequest,
  ) {
    ProvideDesktopCompositionLocals {
      content.invoke()
    }
  }
}

@Composable
private fun FrameWindowScope.ProvideDesktopCompositionLocals(
  content: @Composable () -> Unit,
) {
  val holder = remember {
    PreComposeWindowHolder()
  }
  CompositionLocalProvider(
    LocalLifecycleOwner provides holder,
    LocalViewModelStoreOwner provides holder,
    LocalBackDispatcherOwner provides holder,
    LocalComposeWindow provides window
  ) {
    content.invoke()
  }
}

private class PreComposeWindowHolder : LifecycleOwner, ViewModelStoreOwner, BackDispatcherOwner {
  override val lifecycle by lazy { LifecycleRegistry() }
  override val viewModelStore by lazy { ViewModelStore() }
  override val backDispatcher by lazy { BackDispatcher() }
}
