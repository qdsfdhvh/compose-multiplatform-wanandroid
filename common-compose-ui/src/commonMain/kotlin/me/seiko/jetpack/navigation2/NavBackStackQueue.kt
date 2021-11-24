package me.seiko.jetpack.navigation2

import androidx.compose.runtime.mutableStateListOf

open class NavBackStackQueue : Navigator {

  internal val backStacks = mutableStateListOf<NavBackStackEntry>()

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
    backStacks.add(command.entry)
  }

  private fun replace(command: Command.Replace) {
    backStacks[backStacks.lastIndex] = command.entry
  }

  private fun backTo(command: Command.BackTo) {
    val options = command.options
    var index = backStacks.indexOfLast { it.scene.route == options.popUpToRoute }
    if (index == -1) {
      backStacks.add(command.entry)
      return
    }

    if (!options.popUpToInclusive) index += 1
    backStacks.removeRange(index, backStacks.size)
  }

  private fun back() {
    backStacks.removeLast()
  }

  fun canBack() = backStacks.size > 1
}
