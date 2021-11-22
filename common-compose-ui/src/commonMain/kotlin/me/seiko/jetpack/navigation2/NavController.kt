package me.seiko.jetpack.navigation2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

open class NavController {

  private val commandBuffer = CommandBuffer()
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
      for (scene in value.scenes) {
        routeParser.insert(scene)
      }

      // initial
      navigate(value.initialRoute)
    }

  private var navBackStackEntryId = Long.MIN_VALUE

  fun getNavigatorHolder(): Navigator.Holder = commandBuffer

  private fun navigate(vararg commands: Command) {
    commandBuffer.executeCommands(commands)
  }

  fun navigate(route: String) {
    navigate(Command.Forward(createBackStackEntry(route)))
  }

  fun newRoot(route: String) {
    navigate(
      Command.BackTo(null),
      Command.Replace(createBackStackEntry(route))
    )
  }

  fun replace(route: String) {
    navigate(Command.Replace(createBackStackEntry(route)))
  }

  fun popTo(route: String) {
    navigate(Command.BackTo(createBackStackEntry(route)))
  }

  fun pop() {
    navigate(Command.Back)
  }

  private fun findScene(path: String): Scene {
    val matchResult = routeParser.find(path)
    checkNotNull(matchResult) { "navigate target $path not found" }
    return matchResult.scene
  }

  private fun createBackStackEntry(scene: Scene, rawQuery: String): NavBackStackEntry {
    return NavBackStackEntry(
      id = navBackStackEntryId++,
      scene = scene,
      rawQuery = rawQuery
    )
  }

  private fun createBackStackEntry(route: String): NavBackStackEntry {
    val path = route.substringBefore('?')
    val query = route.substringAfter('?', "")
    val scene = findScene(path)
    return createBackStackEntry(scene, query)
  }
}

@Composable
fun rememberNavController(): NavController {
  return remember { NavController() }
}

@Composable
fun NavController.collectBackStackEntryAsState(): State<NavBackStackEntry?> {
  return getNavigatorHolder().currentBackStackEntryFlow.collectAsState(null)
}