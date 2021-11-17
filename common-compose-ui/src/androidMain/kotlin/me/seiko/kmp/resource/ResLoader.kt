package me.seiko.kmp.resource

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

actual class ResLoader(
  private val context: Context
) {

  actual fun getString(res: StringResource, vararg args: Any): String {
    return context.getString(res.resourceId, args)
  }

  @Composable
  actual fun getSvg(res: FileResource): Painter {
    return rememberImagePainter(
      data = getResPath(context, res.rawResId)
    )
  }

  @Composable
  actual fun getImage(res: ImageResource): Painter {
    return rememberImagePainter(
      data = getResPath(context, res.drawableResId)
    )
  }
}

private fun getResPath(context: Context, resId: Int): String {
  return "android.resource://${context.packageName}/drawable/${context.resources.getResourceEntryName(resId)}"
}
