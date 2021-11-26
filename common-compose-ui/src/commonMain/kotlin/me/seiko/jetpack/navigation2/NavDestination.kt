package me.seiko.jetpack.navigation2

data class NavDestination internal constructor(
  val scene: Scene,
  val navigator: Navigator,
) {

  internal fun createEntry(
    rawQuery: String,
    viewModel: NavControllerViewModel
  ) = NavBackStackEntry(
    id = navBackStackEntryId++,
    scene = scene,
    rawQuery = rawQuery,
    navigator = navigator,
    viewModel = viewModel
  )

  companion object {
    private var navBackStackEntryId = 0L
  }
}
