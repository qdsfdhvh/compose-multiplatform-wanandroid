package me.seiko.jetpack.navigation2

interface Navigator {
  fun applyCommands(commands: Array<out Command>)
}
