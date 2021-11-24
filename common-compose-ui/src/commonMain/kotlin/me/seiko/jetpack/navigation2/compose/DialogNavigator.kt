package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.snapshots.SnapshotStateList
import me.seiko.jetpack.navigation2.Command
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.Navigator

class DialogNavigator : Navigator {

  internal val backStacks = SnapshotStateList<NavBackStackEntry>()

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
    backStacks.add(command.entry)
  }

  internal fun dismiss() {
    backStacks.removeLast()
  }
}
