package me.seiko.compose.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import javafx.embed.swing.JFXPanel
import java.awt.BorderLayout
import java.awt.Container
import javax.swing.JPanel

/**
 * Fork: https://github.com/fuurin-projects/fuurin-editor-s/blob/main/src/main/kotlin/fuurineditor/ui/compose/JavaFXPanel.kt
 *
 * ComposeでJavaFXを使用できるようにするブリッジコンポーネント
 *
 * 参考：https://github.com/JetBrains/compose-jb/issues/519
 */
@Composable
fun JavaFXPanel(
  root: Container,
  panel: JFXPanel,
  onCreate: () -> Unit,
) {
  val container = remember {
    JPanel().apply { BorderLayout(0, 0) }
  }

  val density = LocalDensity.current.density

  Layout(
    content = { },
    modifier = Modifier.fillMaxSize().onGloballyPositioned { childCoordinates ->
      val coordinates = childCoordinates.parentCoordinates!!
      val location = coordinates.localToWindow(Offset.Zero).round()
      val size = coordinates.size
      container.setBounds(
        (location.x / density).toInt(),
        (location.y / density).toInt(),
        (size.width / density).toInt(),
        (size.height / density).toInt()
      )
      container.validate()
      container.repaint()
    },
    measurePolicy = { _, _ ->
      layout(0, 0) {}
    }
  )

  DisposableEffect(Unit) {
    container.add(panel)
    root.add(container)

    onCreate.invoke()

    onDispose {
      panel.removeAll()
      root.remove(container)
    }
  }
}
