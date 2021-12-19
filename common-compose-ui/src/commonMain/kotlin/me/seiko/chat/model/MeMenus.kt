package me.seiko.chat.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource
import me.seiko.chat.MR

enum class MeMenus(
  val icon: () -> ImageVector,
  val title: StringResource,
  val route: String,
) {
  Coin(
    icon = { Icons.Filled.StarBorder },
    title = MR.strings.scene_me_menu_coin,
    route = "",
  ),
  Share(
    icon = { Icons.Filled.Share },
    title = MR.strings.scene_me_menu_share,
    route = "",
  ),
  Favourite(
    icon = { Icons.Filled.FavoriteBorder },
    title = MR.strings.scene_me_menu_favourite,
    route = "",
  ),
  Bookmark(
    icon = { Icons.Filled.BookmarkBorder },
    title = MR.strings.scene_me_menu_bookmark,
    route = "",
  ),
  History(
    icon = { Icons.Filled.History },
    title = MR.strings.scene_me_menu_history,
    route = "",
  ),
  About(
    icon = { Icons.Filled.ErrorOutline },
    title = MR.strings.scene_me_menu_about,
    route = "",
  ),
  Setting(
    icon = { Icons.Filled.Settings },
    title = MR.strings.scene_me_menu_setting,
    route = "",
  )
}
