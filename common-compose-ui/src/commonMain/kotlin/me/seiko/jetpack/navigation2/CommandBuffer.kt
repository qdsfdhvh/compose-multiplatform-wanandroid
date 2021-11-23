package me.seiko.jetpack.navigation2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommandBuffer : Navigator.Holder {

  private val _currentBackStack: MutableSharedFlow<NavBackStackEntry?> =
    MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override val currentBackStack: Flow<NavBackStackEntry?>
    get() = _currentBackStack.asSharedFlow()

  private var navigator: Navigator? = null

  private val pendingCommands = mutableListOf<Array<out Command>>()

  private lateinit var scope: CoroutineScope

  override fun setNavigator(navigator: Navigator) {
    this.navigator = navigator
    pendingCommands.forEach { navigator.applyCommands(it) }
    pendingCommands.clear()

    scope = MainScope()
    scope.launch {
      navigator.backStacks.collect {
        _currentBackStack.tryEmit(it.lastOrNull())
      }
    }
  }

  override fun removeNavigator() {
    scope.cancel()
    this.navigator = null
  }

  fun executeCommands(commands: Array<out Command>) {
    navigator?.applyCommands(commands) ?: pendingCommands.add(commands)
  }
}
