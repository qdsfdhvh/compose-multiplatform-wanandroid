package me.seiko.jetpack.navigation2.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.seiko.jetpack.navigation2.Command
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.Navigator

class ComposeNavigator : Navigator {

  private val _backStacks = MutableStateFlow<List<NavBackStackEntry>>(emptyList())

  override val backStacks: StateFlow<List<NavBackStackEntry>> = _backStacks.asStateFlow()

  override fun applyCommands(commands: Array<out Command>) {
    for (command in commands) {
      applyCommand(command)
    }
  }

  private fun applyCommand(command: Command) {
    when (command) {
      is Command.Forward -> forward(command)
      is Command.Replace -> replace(command)
      is Command.BackTo -> backTo(command)
      Command.Back -> back()
    }
  }

  private fun forward(command: Command.Forward) {
    _backStacks.value = _backStacks.value + command.entry
  }

  private fun replace(command: Command.Replace) {
    _backStacks.value = _backStacks.value - _backStacks.value.last() + command.entry
  }

  private fun backTo(command: Command.BackTo) {
    _backStacks.value = _backStacks.value.takeWhile { it.scene != command.entry?.scene }
  }

  private fun back() {
    _backStacks.value = _backStacks.value - _backStacks.value.last()
  }
}
