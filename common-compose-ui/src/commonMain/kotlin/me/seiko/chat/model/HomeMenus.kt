package me.seiko.chat.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource
import me.seiko.chat.MR
import me.seiko.chat.scene.Routes

enum class HomeMenus(
  val icon: ImageVector,
  val title: StringResource,
  val route: String,
) {
  TimeLine(
    icon = Icons.Outlined.Home,
    title = MR.strings.scene_title_timeline,
    route = Routes.TimeLine,
  ),
  Knowledge(
    icon = Icons.Outlined.Sms,
    title = MR.strings.scene_title_knowledge,
    route = Routes.Knowledge,
  ),
  Search(
    icon = Icons.Outlined.Search,
    title = MR.strings.scene_title_search,
    route = Routes.Search,
  ),
  Me(
    icon = Icons.Outlined.Person,
    title = MR.strings.scene_title_me,
    route = Routes.Me,
  )
}
