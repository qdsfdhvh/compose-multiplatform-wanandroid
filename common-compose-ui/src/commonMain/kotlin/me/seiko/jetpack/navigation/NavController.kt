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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Creates a [Navigator] that controls the [NavHost].
 *
 * @see NavHost
 */
@Composable
fun rememberNavController(): NavController {
  return remember { NavController() }
}

class NavController {
  // FIXME: 2021/4/1 Temp workaround for deeplink
  private var pendingNavigation: String? = null
  internal var stackManager: RouteStackManager? = null
    set(value) {
      field = value
      value?.let {
        pendingNavigation?.let { it1 -> it.navigate(it1) }
      }
    }

  /**
   * Navigate to a route in the current RouteGraph.
   *
   * @param route route for the destination
   * @param options navigation options for the destination
   */
  fun push(route: String, options: NavOptions? = null) {
    stackManager?.navigate(route, options) ?: run {
      pendingNavigation = route
    }
  }

  suspend fun navigateForResult(route: String, options: NavOptions? = null): Any? {
    stackManager?.navigate(route, options) ?: run {
      pendingNavigation = route
      return null
    }
    val currentEntry = stackManager?.currentEntry ?: return null
    return stackManager?.waitingForResult(currentEntry)
  }

  /**
   * Attempts to navigate up in the navigation hierarchy. Suitable for when the
   * user presses the "Up" button marked with a left (or start)-facing arrow in the upper left
   * (or starting) corner of the app UI.
   */
  fun pop() {
    stackManager?.pop()
  }

  fun popWith(result: Any? = null) {
    stackManager?.pop(result)
  }

  /**
   * Check if navigator can navigate up
   */
  val canGoBack: Boolean
    get() = stackManager?.canGoBack ?: false
}
