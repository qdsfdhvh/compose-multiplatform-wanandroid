package me.seiko.jetpack.navigation2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
  val backStacks: StateFlow<List<NavBackStackEntry>>
  fun applyCommands(commands: Array<out Command>)

  interface Holder {
    val currentBackStack: Flow<NavBackStackEntry?>
    fun setNavigator(navigator: Navigator)
    fun removeNavigator()
  }
}
