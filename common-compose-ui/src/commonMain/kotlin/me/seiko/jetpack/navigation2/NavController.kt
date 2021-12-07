package me.seiko.jetpack.navigation2

import me.seiko.jetpack.dispatcher.BackDispatcher
import me.seiko.jetpack.dispatcher.BackHandler
import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver
import me.seiko.jetpack.lifecycle.LifecycleOwner
import me.seiko.jetpack.viewmodel.ViewModelStore

open class NavController : LifecycleObserver, BackHandler {

  val navigatorProvider = NavigatorProvider()

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
      navigate(value.initialRoute)
    }

  var backDispatcher: BackDispatcher? = null
    internal set(value) {
      if (value === field) return
      field?.unregister(this)
      field = value
      value?.register(this)
    }

  var lifecycleOwner: LifecycleOwner? = null
    internal set(owner) {
      if (owner === field) return
      field?.lifecycle?.removeObserver(this)
      field = owner
      owner?.lifecycle?.addObserver(this)
    }

  internal var viewModel: NavControllerViewModel? = null

  internal fun setViewModelStore(viewModelStore: ViewModelStore) {
    if (viewModel != NavControllerViewModel.create(viewModelStore)) {
      viewModel = NavControllerViewModel.create(viewModelStore)
    }
  }

  fun navigate(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    val node = findNavDestination(route)
    val entry = node.createEntry(viewModel!!, route)

    if (builder == null) {
      forward(entry)
      return
    }

    val options = navOptions(builder)

    val currentBackStack = currentBackStack
    if (options.singleTop && currentBackStack != null) {
      if (currentBackStack.scene.matches(route)) {
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
    entry.navigator.applyCommands(commands)
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
    return pop()
  }

  fun findNavDestination(route: String): NavDestination {
    return checkNotNull(graph.findNode(route)) {
      "navigate target $route not found"
    }
  }
}
