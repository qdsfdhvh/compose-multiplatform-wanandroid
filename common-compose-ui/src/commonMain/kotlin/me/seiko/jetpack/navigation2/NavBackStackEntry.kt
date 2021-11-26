package me.seiko.jetpack.navigation2

import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleObserver
import me.seiko.jetpack.lifecycle.LifecycleOwner
import me.seiko.jetpack.lifecycle.LifecycleRegistry
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner
import kotlin.reflect.KClass

class NavBackStackEntry internal constructor(
  val id: Long,
  val scene: Scene,
  private val rawQuery: String,
  private val viewModel: NavControllerViewModel,
  internal val navigator: Navigator,
) : ViewModelStoreOwner, LifecycleOwner {

  private val queryString by lazy { parseRawQuery(rawQuery) }

  override val viewModelStore: ViewModelStore
    get() = viewModel.get(id = id)

  private val lifecycleRegistry by lazy {
    LifecycleRegistry()
  }

  override val lifecycle: Lifecycle
    get() = lifecycleRegistry

  init {
    lifecycle.addObserver(object : LifecycleObserver {
      override fun onStateChanged(state: Lifecycle.State) {
        viewModel.clear(id)
        lifecycle.removeObserver(this)
      }
    })
  }

  internal fun onActive() {
    lifecycleRegistry.currentState = Lifecycle.State.Active
  }

  internal fun onInActive() {
    lifecycleRegistry.currentState = Lifecycle.State.InActive
  }

  internal fun onDestroy() {
    // 由于使用了多个Navigator，所以这里过滤下防止重复onDestroy
    if (lifecycleRegistry.currentState === Lifecycle.State.Destroyed) return

    lifecycleRegistry.currentState = Lifecycle.State.Destroyed
    viewModelStore.clear()
  }

  fun <T : Any> query(name: String, clazz: KClass<T>): T? {
    val value = queryString[name]?.firstOrNull() ?: return null
    return convertValue(clazz, value)
  }

  inline fun <reified T : Any> query(name: String): T? {
    return query(name, T::class)
  }

  inline fun <reified T : Any> query(name: String, default: T): T {
    return query(name, T::class) ?: default
  }

  fun <T : Any> queryList(name: String, clazz: KClass<T>): List<T> {
    val values = queryString[name] ?: return emptyList()
    return values.map { convertValue(clazz, it) }
  }

  inline fun <reified T : Any> queryList(name: String): List<T> {
    return queryList(name, T::class)
  }
}

private fun <T : Any> convertValue(clazz: KClass<T>, value: String): T {
  @Suppress("UNCHECKED_CAST")
  return when (clazz) {
    Int::class -> value.toIntOrNull()
    Long::class -> value.toLongOrNull()
    String::class -> value
    Boolean::class -> value.toBooleanStrictOrNull()
    Float::class -> value.toFloatOrNull()
    Double::class -> value.toDoubleOrNull()
    else -> throw NotImplementedError()
  } as T
}

// aa=1&bb=2&aa=3 -> { aa: [1,3], bb: [2] }
private fun parseRawQuery(rawQuery: String): Map<String, List<String>> {
  val map = mutableMapOf<String, MutableList<String>>()

  val rawParams = rawQuery.split('&')
  if (rawParams.isEmpty()) return map

  for (rawParam in rawParams) {
    val array = rawParam.split('=')
    if (array.size != 2 ||
      array.firstOrNull().isNullOrEmpty() ||
      array.lastOrNull().isNullOrEmpty()
    ) continue

    val key = array.first()
    if (map.contains(key)) {
      map[key]!! += array.last()
    } else {
      map[key] = mutableListOf(array.last())
    }
  }
  return map
}
