package me.seiko.chat.scene.home.bottom

import me.seiko.chat.base.BaseViewModel
import kotlin.random.Random

class MeViewModel : BaseViewModel() {
  val id = Random.nextInt(100)
}
