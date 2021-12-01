package me.seiko.jetpack.navigation2

data class NavDestination internal constructor(
  val scene: Scene,
  val navigator: Navigator,
) {

  internal fun createEntry(
    viewModel: NavControllerViewModel,
    id: Long = getEntryId(),
    path: String = "",
    rawQuery: String = "",
  ) = NavBackStackEntry(
    id = id,
    scene = scene,
    path = path,
    rawQuery = rawQuery,
    navigator = navigator,
    viewModel = viewModel
  )

  companion object {

    internal fun getEntryId() = navBackStackEntryId++

    private var navBackStackEntryId = 0L
  }
}
