package me.seiko.chat.base

import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.util.Logger

abstract class BaseViewModel : ViewModel() {
  override fun onCleared() {
    Logger.d { "${this::class.simpleName} onCleared" }
  }
}
