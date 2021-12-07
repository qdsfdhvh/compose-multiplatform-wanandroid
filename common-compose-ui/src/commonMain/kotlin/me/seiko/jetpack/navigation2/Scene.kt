package me.seiko.jetpack.navigation2

interface Scene {
  fun matches(route: String): Boolean
}

interface RouteScene : Scene {
  val route: String
  val exact: Boolean get() = false

  override fun matches(route: String): Boolean {
    val path = route.substringBefore('?')
    return if (exact && !path.contains('{')) {
      this.route == path
    } else {
      var routeRegex = this.route
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
}

interface RegexScene : Scene {
  val regex: Regex
  override fun matches(route: String): Boolean {
    return regex.matches(route)
  }
}
