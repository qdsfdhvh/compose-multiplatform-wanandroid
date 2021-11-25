package me.seiko.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.Insets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsHeight

@Composable
actual fun PlatformInsets(
  control: NativeInsetsControl,
  color: NativeInsetsColor,
  content: @Composable () -> Unit
) {
  Box {
    val insets = provideSystemInsets(control)
    Box(
      modifier = Modifier
        .padding(rememberInsetsPaddingValues(insets))
        .align(Alignment.Center)
    ) {
      content()
    }

    if (!control.extendToTop) {
      Spacer(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .background(color.top)
          .fillMaxWidth()
          .statusBarsHeight()
      )
    }

    if (!control.extendToBottom) {
      Spacer(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .background(color.bottom)
          .fillMaxWidth()
          .navigationBarsHeight()
      )
    }
  }
}

@Composable
private fun provideSystemInsets(
  control: NativeInsetsControl
): Insets {
  val ime = LocalWindowInsets.current.ime
  val statusBars = LocalWindowInsets.current.statusBars
  val navigationBars = LocalWindowInsets.current.navigationBars

  return key(statusBars, navigationBars) {
    ime.copy(
      top = if (control.extendToTop) 0 else statusBars.top,
      bottom = if (control.extendToBottom) 0 else navigationBars.bottom
    )
  }
}
