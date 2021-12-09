package me.seiko.chat.scene.home.bottom.knowledge

import me.seiko.chat.base.BaseViewModel
import me.seiko.chat.model.KnowledgeTab

class KnowledgeViewModel : BaseViewModel() {

  val tabs = KnowledgeTab.values().toList()
}
