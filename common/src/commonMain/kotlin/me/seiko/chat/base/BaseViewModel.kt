package me.seiko.chat.base

import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.util.Logger

abstract class BaseViewModel : ViewModel() {
  override fun onCleared() {
    Logger.d(tag = "BaseViewModel") {
      "${this::class.simpleName}@${this.hashCode().toString(16)} onCleared"
    }
  }
}
