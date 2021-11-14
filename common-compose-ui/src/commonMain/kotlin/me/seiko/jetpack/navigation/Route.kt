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
import me.seiko.jetpack.navigation.transition.NavTransition

interface Route {
  val route: String
  val pathKeys: List<String>
}

abstract class ComposeRoute(
  override val route: String,
  val content: @Composable (NavBackStackEntry) -> Unit
) : Route {
  override val pathKeys: List<String> by lazy {
    RouteParser.pathKeys(route)
  }
}

internal class DialogRoute(
  route: String,
  content: @Composable (NavBackStackEntry) -> Unit
) : ComposeRoute(route, content)

internal class SceneRoute(
  route: String,
  val navTransition: NavTransition?,
  val deepLinks: List<String>,
  content: @Composable (NavBackStackEntry) -> Unit,
) : ComposeRoute(route, content) {
  override val pathKeys by lazy {
    (
      deepLinks.flatMap {
        RouteParser.pathKeys(pattern = it)
      } + RouteParser.pathKeys(pattern = route)
      ).distinct()
  }
}
