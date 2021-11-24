package me.seiko.jetpack.navigation2

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.seiko.jetpack.dispatcher.BackDispatcher
import me.seiko.jetpack.dispatcher.BackHandler
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver
import me.seiko.jetpack.lifecycle.LifecycleOwner

open class NavController : LifecycleObserver, BackHandler {

  private val _backStackQueue = mutableStateListOf<NavBackStackEntry>()

  val backStackQueue: SnapshotStateList<NavBackStackEntry>
    get() = _backStackQueue

  val currentBackStack: NavBackStackEntry?
    get() = _backStackQueue.lastOrNull()

  val navigatorProvider = NavigatorProvider()

  private val routeParser = RouteParser()

  private var _graph: NavGraph? = null

  open var graph: NavGraph
    get() {
      checkNotNull(_graph) { "You must call setGraph() before calling getGraph()" }
      return _graph as NavGraph
    }
    set(value) {
      _graph = value

      // bind route
      routeParser.clear()
      for (node in value.nodes) {
        routeParser.insert(node)
      }

      // initial
      navigate(value.initialRoute)
    }

  private var navBackStackEntryId = 0L

  var backDispatcher: BackDispatcher? = null
    set(value) {
      field?.unregister(this)
      field = value
      value?.register(this)
    }

  var lifecycleOwner: LifecycleOwner? = null
    set(owner) {
      if (owner == field) return
      field?.lifecycle?.removeObserver(this)
      field = owner
      owner?.lifecycle?.addObserver(this)
    }

  fun navigate(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    val path = route.substringBefore('?')
    val rawQuery = route.substringAfter('?', "")

    val node = findNavDestination(path)
    val entry = createBackStackEntry(node, rawQuery)

    if (builder == null) {
      forward(entry)
      return
    }

    val options = navOptions(builder)
    if (options.singleTop) {
      if (route == currentBackStack?.scene?.route) {
        return
      }
    }

    if (options.popUpToRoute.isNotEmpty()) {
      backTo(entry, options)
      return
    }

    forward(entry)
  }

  private fun forward(entry: NavBackStackEntry) {
    _backStackQueue.add(entry)
    applyCommands(entry, Command.Forward(entry))
  }

  private fun backTo(entry: NavBackStackEntry, options: NavOptions) {
    var index = _backStackQueue.indexOfLast { it.scene.route == options.popUpToRoute }
    if (index == -1) {
      forward(entry)
      return
    } else if (index == _backStackQueue.lastIndex) {
      return
    }

    if (!options.popUpToInclusive) index += 1
    _backStackQueue.removeRange(index, _backStackQueue.size)
    applyCommands(entry, Command.BackTo(entry, options.popUpToInclusive))
  }

  private fun back(entry: NavBackStackEntry) {
    _backStackQueue.removeLast()
    applyCommands(entry, Command.Back)
  }

  fun pop(): Boolean {
    if (_backStackQueue.size > 1) {
      back(currentBackStack!!)
      return true
    }
    return false
  }

  private fun applyCommands(entry: NavBackStackEntry, vararg commands: Command) {
    navigatorProvider.get(entry.navigatorName)?.applyCommands(commands)
  }

  override fun onStateChanged(state: Lifecycle.State) {
  }

  override fun handleBackPress(): Boolean {
    if (pop()) return true
    return false
  }

  private fun findNavDestination(path: String): NavDestination {
    val matchResult = routeParser.find(path)
    checkNotNull(matchResult) { "navigate target $path not found" }
    return matchResult.node
  }

  private fun createBackStackEntry(
    node: NavDestination,
    rawQuery: String,
  ) = NavBackStackEntry(
    id = navBackStackEntryId++,
    scene = node.scene,
    rawQuery = rawQuery,
    navigatorName = node.navigatorName
  )
}
