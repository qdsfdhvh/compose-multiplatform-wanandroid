package me.seiko.jetpack.navigation2.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.seiko.jetpack.navigation2.Command
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.Navigator

class DialogNavigator : Navigator {

  private val _backStacks = MutableStateFlow<List<NavBackStackEntry>>(emptyList())

  internal val backStacks: StateFlow<List<NavBackStackEntry>> = _backStacks.asStateFlow()

  override fun applyCommands(commands: Array<out Command>) {
    for (command in commands) {
      applyCommand(command)
    }
  }

  private fun applyCommand(command: Command) {
    when (command) {
      is Command.Forward -> show(command)
      Command.Back -> dismiss()
      else -> Unit
    }
  }

  private fun show(command: Command.Forward) {
    _backStacks.value = _backStacks.value + command.entry
  }

  internal fun dismiss() {
    _backStacks.value = _backStacks.value - _backStacks.value.last()
  }
}
