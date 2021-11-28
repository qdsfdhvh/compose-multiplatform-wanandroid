package me.seiko.jetpack.navigation2

class NavGraph internal constructor(
  val initialRoute: String,
  val nodes: List<NavDestination>
)

class NavGraphBuilder internal constructor(
  val provider: NavigatorProvider,
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

internal fun NavGraph.findNode(path: String, exact: Boolean = false): NavDestination? {
  return nodes.find { it.matches(path, exact) }
}

private fun NavDestination.matches(path: String, exact: Boolean): Boolean {
  return if (exact && !path.contains('{')) {
    scene.route == path
  } else {
    var routeRegex = scene.route
    var indexOfParam = routeRegex.indexOf('{')
    while (indexOfParam != -1) {
      val param = routeRegex.substring(indexOfParam..routeRegex.indexOf('}', indexOfParam))
      routeRegex = routeRegex.replaceFirst(param, "[^/;]+")
      indexOfParam = routeRegex.indexOf('{')
    }
    routeRegex += if (exact) "$" else ".*"
    path.matches(routeRegex.toRegex())
  }
}
