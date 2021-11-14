/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 *
 *  This file is part of Twidere X.
 *
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package me.seiko.jetpack.navigation

import me.seiko.jetpack.lifecycle.Lifecycle
import me.seiko.jetpack.lifecycle.LifecycleOwner
import me.seiko.jetpack.lifecycle.LifecycleRegistry
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner

class NavBackStackEntry internal constructor(
  val id: Long,
  val route: ComposeRoute,
  val pathMap: Map<String, String>,
  val queryString: QueryString? = null,
  internal val viewModel: NavControllerViewModel,
) : ViewModelStoreOwner, LifecycleOwner {
  private var destroyAfterTransition = false

  override val viewModelStore: ViewModelStore
    get() = viewModel.get(id = id)

  private val lifecycleRegistry by lazy { LifecycleRegistry() }

  override val lifecycle: Lifecycle
    get() = lifecycleRegistry

  fun onActive() {
    lifecycleRegistry.currentState = Lifecycle.State.Active
  }

  fun onInActive() {
    lifecycleRegistry.currentState = Lifecycle.State.InActive
    if (destroyAfterTransition) {
      onDestroy()
    }
  }

  fun onDestroy() {
    if (lifecycleRegistry.currentState != Lifecycle.State.InActive) {
      destroyAfterTransition = true
    } else {
      lifecycleRegistry.currentState = Lifecycle.State.Destroyed
      viewModelStore.clear()
    }
  }
}

inline fun <reified T> NavBackStackEntry.path(path: String, default: T? = null): T? {
  val value = pathMap[path] ?: return default
  return convertValue(value)
}

inline fun <reified T> NavBackStackEntry.query(name: String, default: T? = null): T? {
  return queryString?.query(name, default)
}

inline fun <reified T> NavBackStackEntry.queryList(name: String): List<T?> {
  val value = queryString?.map?.get(name) ?: return emptyList()
  return value.map { convertValue(it) }
}

inline fun <reified T> convertValue(value: String): T? {
  return when (T::class) {
    Int::class -> value.toIntOrNull()
    Long::class -> value.toLongOrNull()
    String::class -> value
    Boolean::class -> value.toBooleanStrictOrNull()
    Float::class -> value.toFloatOrNull()
    Double::class -> value.toDoubleOrNull()
    else -> throw NotImplementedError()
  } as T
}
