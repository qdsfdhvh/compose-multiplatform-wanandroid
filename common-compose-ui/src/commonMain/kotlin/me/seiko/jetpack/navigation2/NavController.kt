package me.seiko.jetpack.navigation2

import me.seiko.jetpack.dispatcher.BackDispatcher
import me.seiko.jetpack.dispatcher.BackHandler
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver
import me.seiko.jetpack.lifecycle.LifecycleOwner
import me.seiko.jetpack.viewmodel.ViewModelStore

open class NavController : LifecycleObserver, BackHandler {

  val navigatorProvider = NavigatorProvider()

  private val routeParser = RouteParser()
  private val backStackQueue = NavBackStackQueue()

  val currentBackStack: NavBackStackEntry?
    get() = backStackQueue.backStacks.lastOrNull()

  private var _graph: NavGraph? = null

  open var graph: NavGraph
    get() {
      checkNotNull(_graph) { "You must call setGraph() before calling getGraph()" }
      return _graph as NavGraph
    }
    set(value) {
      _graph = value
      routeParser.setNodes(value.nodes)
      navigate(value.initialRoute)
    }

  var backDispatcher: BackDispatcher? = null
    internal set(value) {
      field?.unregister(this)
      field = value
      value?.register(this)
    }

  var lifecycleOwner: LifecycleOwner? = null
    internal set(owner) {
      if (owner == field) return
      field?.lifecycle?.removeObserver(this)
      field = owner
      owner?.lifecycle?.addObserver(this)
    }

  private var viewModel: NavControllerViewModel? = null

  internal fun setViewModelStore(viewModelStore: ViewModelStore) {
    if (viewModel != NavControllerViewModel.create(viewModelStore)) {
      viewModel = NavControllerViewModel.create(viewModelStore)
    }
  }

  fun navigate(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    val path = route.substringBefore('?')
    val rawQuery = route.substringAfter('?', "")

    val node = findNavDestination(path)
    val entry = node.createEntry(rawQuery, viewModel!!)

    if (builder == null) {
      forward(entry)
      return
    }

    val options = navOptions(builder)

    val currentBackStack = currentBackStack
    if (options.singleTop && currentBackStack != null) {
      if (route == currentBackStack.scene.route) {
        return
      }
    }

    if (options.popUpToRoute.isNotEmpty()) {
      backTo(entry, options)
      return
    }

    forward(entry)
  }

  fun pop(): Boolean {
    if (backStackQueue.canBack()) {
      back(currentBackStack!!)
      return true
    }
    return false
  }

  private fun forward(entry: NavBackStackEntry) {
    applyCommands(entry, Command.Forward(entry))
  }

  private fun backTo(entry: NavBackStackEntry, options: NavOptions) {
    applyCommands(entry, Command.BackTo(entry, options))
  }

  private fun back(entry: NavBackStackEntry) {
    applyCommands(entry, Command.Back)
  }

  private fun applyCommands(entry: NavBackStackEntry, vararg commands: Command) {
    backStackQueue.applyCommands(commands)
    navigatorProvider.get(entry.navigatorName)?.applyCommands(commands)
  }

  override fun onStateChanged(state: Lifecycle.State) {
    when (state) {
      Lifecycle.State.Initialized -> Unit
      Lifecycle.State.Active -> currentBackStack?.onActive()
      Lifecycle.State.InActive -> currentBackStack?.onInActive()
      Lifecycle.State.Destroyed -> backStackQueue.onCleared()
    }
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
}
