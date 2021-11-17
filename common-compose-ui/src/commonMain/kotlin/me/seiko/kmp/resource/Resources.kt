package me.seiko.kmp.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import me.seiko.kmp.LocalResLoader

@Composable
fun stringResource(res: StringResource): String {
  return LocalResLoader.current.getString(res)
}

@Composable
fun painterResource(res: ImageResource): Painter {
  return LocalResLoader.current.getImage(res)
}

@Composable
fun painterResource(res: FileResource): Painter {
  return LocalResLoader.current.getSvg(res)
}
