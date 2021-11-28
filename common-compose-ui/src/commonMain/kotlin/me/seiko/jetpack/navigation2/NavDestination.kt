package me.seiko.jetpack.navigation2

data class NavDestination internal constructor(
  val scene: Scene,
  val navigator: Navigator,
) {

  internal fun createEntry(
    viewModel: NavControllerViewModel,
    path: String = "",
    rawQuery: String = "",
  ) = NavBackStackEntry(
    id = navBackStackEntryId++,
    scene = scene,
    path = path,
    rawQuery = rawQuery,
    navigator = navigator,
    viewModel = viewModel
  )

  companion object {
    private var navBackStackEntryId = 0L
  }
}
