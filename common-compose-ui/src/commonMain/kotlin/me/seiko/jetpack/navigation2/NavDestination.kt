package me.seiko.jetpack.navigation2

import kotlin.reflect.KClass

data class NavDestination internal constructor(
  val scene: Scene,
  val navigatorName: KClass<out Navigator>,
) {

  internal fun createEntry(
    rawQuery: String,
    viewModel: NavControllerViewModel
  ) = NavBackStackEntry(
    id = navBackStackEntryId++,
    scene = scene,
    rawQuery = rawQuery,
    navigatorName = navigatorName,
    viewModel = viewModel
  )

  companion object {
    private var navBackStackEntryId = 0L
  }
}
