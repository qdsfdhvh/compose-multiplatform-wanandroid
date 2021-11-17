package me.seiko.kmp.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

expect class ResLoader {
  fun getString(res: StringResource, vararg args: Any): String

  @Composable
  fun getSvg(res: FileResource): Painter

  @Composable
  fun getImage(res: ImageResource): Painter
}
