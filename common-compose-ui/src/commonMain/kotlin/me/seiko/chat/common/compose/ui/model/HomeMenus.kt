package me.seiko.chat.common.compose.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource
import me.seiko.chat.MR

enum class HomeMenus(
  val icon: ImageVector,
  val title: StringResource,
  val route: String,
) {
  TimeLine(
    icon = Icons.Outlined.Home,
    title = MR.strings.scene_title_timeline,
    route = "/timeline",
  ),
  Mention(
    icon = Icons.Outlined.Sms,
    title = MR.strings.scene_title_mention,
    route = "/mention",
  ),
  Search(
    icon = Icons.Outlined.Search,
    title = MR.strings.scene_title_search,
    route = "/search",
  ),
  Me(
    icon = Icons.Outlined.Person,
    title = MR.strings.scene_title_me,
    route = "/me",
  )
}
