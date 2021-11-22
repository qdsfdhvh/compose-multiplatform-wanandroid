package me.seiko.jetpack.navigation2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
  val backStack: StateFlow<List<NavBackStackEntry>>
  fun applyCommands(commands: Array<out Command>)

  interface Holder {
    val currentBackStackEntryFlow: Flow<NavBackStackEntry?>
    fun setNavigator(navigator: Navigator)
    fun removeNavigator()
  }
}
