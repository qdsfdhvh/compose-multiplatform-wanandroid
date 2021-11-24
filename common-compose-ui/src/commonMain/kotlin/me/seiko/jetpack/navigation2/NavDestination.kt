package me.seiko.jetpack.navigation2

import kotlin.reflect.KClass

data class NavDestination(
  val scene: Scene,
  val navigatorName: KClass<out Navigator>,
) {

  fun createEntry(rawQuery: String) = NavBackStackEntry(
    id = navBackStackEntryId++,
    scene = scene,
    rawQuery = rawQuery,
    navigatorName = navigatorName
  )

  companion object {
    private var navBackStackEntryId = 0L
  }
}
