package me.seiko.chat.scene.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.model.HomeMenus
import me.seiko.chat.model.ui.UiUser

class HomeViewModel : BaseViewModel() {
  val state: StateFlow<HomeState> = MutableStateFlow(
    HomeState(
      menus = HomeMenus.values().toList(),
      user = UiUser(
        name = "SeikoDes",
        logo = "https://z3.ax1x.com/2021/11/28/ouJxVx.jpg",
      )
    )
  )
}

data class HomeState(
  val menus: List<HomeMenus>,
  val user: UiUser,
)
