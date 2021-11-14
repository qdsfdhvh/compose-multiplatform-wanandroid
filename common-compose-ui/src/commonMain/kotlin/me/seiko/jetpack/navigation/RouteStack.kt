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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.seiko.jetpack.navigation.transition.NavTransition

@Stable
internal class RouteStack(
  val id: Long,
  val stacks: SnapshotStateList<NavBackStackEntry> = mutableStateListOf(),
  val navTransition: NavTransition? = null,
) {
  private var destroyAfterTransition = false
  val currentEntry: NavBackStackEntry?
    get() = stacks.lastOrNull()

  val canGoBack: Boolean
    get() = stacks.size > 1

  fun goBack(): NavBackStackEntry {
    return stacks.removeLast().also {
      it.onDestroy()
    }
  }

  fun onActive() {
    currentEntry?.onActive()
  }

  fun onInActive() {
    currentEntry?.onInActive()
    if (destroyAfterTransition) {
      onDestroyed()
    }
  }

  fun destroyAfterTransition() {
    destroyAfterTransition = true
  }

  fun onDestroyed() {
    stacks.forEach {
      it.onDestroy()
    }
    stacks.clear()
  }

  fun hasRoute(route: String): Boolean {
    return stacks.any { it.route.route == route }
  }
}
