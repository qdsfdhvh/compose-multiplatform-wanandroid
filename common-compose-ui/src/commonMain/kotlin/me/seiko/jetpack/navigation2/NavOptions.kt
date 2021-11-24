package me.seiko.jetpack.navigation2

class NavOptions(
  val singleTop: Boolean,
  val popUpToRoute: String,
  val popUpToInclusive: Boolean,
)

class NavOptionsBuilder internal constructor() {
  var singleTop = false

  private var popUpToRoute: String = ""
  private var popUpToInclusive: Boolean = false

  fun popUpTo(route: String, inclusive: Boolean = false) {
    popUpToRoute = route
    popUpToInclusive = inclusive
  }

  internal fun build() = NavOptions(
    singleTop = singleTop,
    popUpToRoute = popUpToRoute,
    popUpToInclusive = popUpToInclusive,
  )
}

fun navOptions(builder: NavOptionsBuilder.() -> Unit): NavOptions {
  return NavOptionsBuilder().apply(builder).build()
}
