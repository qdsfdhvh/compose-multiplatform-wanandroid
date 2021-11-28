package me.seiko.kmp.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

@OptIn(ExperimentalComposeUiApi::class)
actual class ResLoader {

  actual fun getString(res: StringResource, vararg args: Any): String {
    return res.localized(args = args)
  }

  @Composable
  actual fun getSvg(res: FileResource): Painter {
    return painterResource(res.filePath)
  }

  @Composable
  actual fun getImage(res: ImageResource): Painter {
    return painterResource(res.filePath)
  }
}
