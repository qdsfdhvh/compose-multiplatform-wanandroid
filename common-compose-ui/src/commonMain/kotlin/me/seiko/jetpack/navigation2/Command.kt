package me.seiko.jetpack.navigation2

sealed interface Command {
  data class Forward(val entry: NavBackStackEntry) : Command
  data class Replace(val entry: NavBackStackEntry) : Command
  data class BackTo(val entry: NavBackStackEntry?) : Command
  object Back : Command
}
