package me.seiko.jetpack.navigation2

class NavGraph internal constructor(
  val initialRoute: String,
  val nodes: List<NavDestination>
)

class NavGraphBuilder(
  private val initialRoute: String,
  private val nodes: MutableList<NavDestination> = mutableListOf()
) {

  fun composable(node: NavDestination) {
    nodes += node
  }

  fun build() = NavGraph(
    initialRoute = initialRoute,
    nodes = nodes
  )
}
