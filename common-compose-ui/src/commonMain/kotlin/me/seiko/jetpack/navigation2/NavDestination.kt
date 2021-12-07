package me.seiko.jetpack.navigation2

data class NavDestination internal constructor(
  val scene: Scene,
  val navigator: Navigator,
) {

  internal fun createEntry(
    viewModel: NavControllerViewModel,
    route: String = "",
    id: Long = getEntryId(),
  ) = NavBackStackEntry(
    id = id,
    scene = scene,
    route = route,
    navigator = navigator,
    viewModel = viewModel
  )

  companion object {

    internal fun getEntryId() = navBackStackEntryId++

    private var navBackStackEntryId = 0L
  }
}
