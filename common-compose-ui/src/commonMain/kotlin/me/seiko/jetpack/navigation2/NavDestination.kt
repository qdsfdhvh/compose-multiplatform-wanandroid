package me.seiko.jetpack.navigation2

import kotlin.reflect.KClass

data class NavDestination(
  val scene: Scene,
  val navigatorName: KClass<out Navigator>,
)
