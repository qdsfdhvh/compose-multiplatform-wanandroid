package me.seiko.chat.scene.home.bottom

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.model.MeMenus
import me.seiko.chat.model.ui.UiUser

class MeViewModel : BaseViewModel() {
  val state: StateFlow<MeState> by lazy {
    MutableStateFlow(
      MeState(
        menus = MeMenus.values().toList(),
        user = UiUser(
          name = "SeikoDes",
          logo = "https://z3.ax1x.com/2021/11/28/ouJxVx.jpg",
        )
      )
    )
  }
}

data class MeState(
  val menus: List<MeMenus>,
  val user: UiUser,
)
