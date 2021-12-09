package me.seiko.jetpack.dispatcher

class BackDispatcher {

  private val handlers = mutableListOf<BackHandler>()

  fun onBackPress(): Boolean {
    handlers.forEach {
      if (it.handleBackPress()) return true
    }
    return false
  }

  fun register(handler: BackHandler) {
    handlers.add(0, handler)
  }

  fun unregister(handler: BackHandler) {
    handlers.remove(handler)
  }
}
