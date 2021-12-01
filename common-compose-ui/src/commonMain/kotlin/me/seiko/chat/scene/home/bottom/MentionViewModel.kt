package me.seiko.chat.scene.home.bottom

import me.seiko.chat.base.BaseViewModel
import kotlin.random.Random

class MentionViewModel : BaseViewModel() {
  val id = Random.nextInt(100)
}
