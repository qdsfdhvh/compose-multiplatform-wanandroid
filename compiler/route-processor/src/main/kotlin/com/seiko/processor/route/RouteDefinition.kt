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
package com.seiko.processor.route

private const val StandardIndent = "  "
private const val RouteDivider = "/"

internal interface RouteDefinition {
  val name: String
  val parent: RouteDefinition?
  // fun generateDefinition(): String
  fun generateRoute(): String
}

internal fun RouteDefinition.parents(): List<RouteDefinition> {
  val list = arrayListOf<RouteDefinition>()
  var p = parent
  while (p != null) {
    list.add(0, p)
    p = p.parent
  }
  return list
}

internal val RouteDefinition.parentPath
  get() = parents()
    .joinToString(RouteDivider) { it.name }

internal val RouteDefinition.indent
  get() = parents()
    .filter { it !is PrefixRouteDefinition }
    .joinToString("") { StandardIndent }

internal data class PrefixRouteDefinition(
  val schema: String,
  val child: NestedRouteDefinition,
  val routeClassName: String,
  // val definitionClassName: String,
) : RouteDefinition {
  override val name: String
    get() = if (schema.isEmpty()) "" else "$schema:$RouteDivider"
  override val parent: RouteDefinition?
    get() = null

  init {
    child.parent = this
  }

  // override fun generateDefinition(): String {
  //   return child.copy(name = definitionClassName).generateDefinition()
  // }

  override fun generateRoute(): String {
    return child.copy(name = routeClassName).generateRoute()
  }
}

internal data class NestedRouteDefinition(
  override val name: String,
  override var parent: RouteDefinition? = null,
  val fullName: String,
  val childRoute: ArrayList<RouteDefinition> = arrayListOf(),
) : RouteDefinition {
  // override fun generateDefinition(): String {
  //   return "${indent}object $name {${System.lineSeparator()}" +
  //     childRoute.joinToString(System.lineSeparator()) { it.generateDefinition() } +
  //     System.lineSeparator() +
  //     "$indent}"
  // }

  override fun generateRoute(): String {
    val isParent = parent is PrefixRouteDefinition

    var overrideRoute: String = ""
    if (!isParent) {
      val functions = childRoute.find { it is FunctionRouteDefinition } as? FunctionRouteDefinition
      if (functions != null) {

        var pathWithParameter = functions.parameters.filter { !it.isNullable }.joinToString(RouteDivider) { parameter ->
          "{${parameter.name}}"
        }
        if (pathWithParameter.isNotEmpty()) {
          pathWithParameter = RouteDivider + pathWithParameter
        }
        overrideRoute = "${indent}${indent}override val route = \"${functions.parentPath}$pathWithParameter\"${System.lineSeparator()}"
      }
    }

    return "${indent}actual object $name ${if (isParent) "" else ": me.seiko.chat.scene.IRoute"} {${System.lineSeparator()}" +
      overrideRoute +
      childRoute.joinToString(System.lineSeparator()) { it.generateRoute() } +
      System.lineSeparator() +
      "$indent}"
  }
}

internal data class ConstRouteDefinition(
  override val name: String,
  override val parent: RouteDefinition? = null,
) : RouteDefinition {
  // override fun generateDefinition(): String {
  //   return "${indent}const val $name = \"$parentPath$RouteDivider${name}\""
  // }

  override fun generateRoute(): String {
    return "${indent}actual val $name = \"${parentPath}$RouteDivider${name}\""
  }
}

internal data class FunctionRouteDefinition(
  override val name: String,
  override val parent: RouteDefinition? = null,
  val parameters: List<RouteParameter>,
) : RouteDefinition {
  // override fun generateDefinition(): String {
  //   val path = parameters
  //     .filter { !it.isNullable }
  //     .joinToString(RouteDivider) { parameter ->
  //       "{${parameter.name}}"
  //     }
  //     .let {
  //       if (it.isNotEmpty()) {
  //         "$RouteDivider$it"
  //       } else {
  //         it
  //       }
  //     }
  //   return "${indent}const val $name = \"$parentPath$RouteDivider$name$path\""
  // }

  override fun generateRoute(): String {
    val query = parameters
      .filter { it.isNullable }
      .joinToString("&") { parameter ->
        val name = parameter.name
        if (parameter.type == "kotlin.String") {
          "$name=${encodeNullable(name)}"
        } else {
          "$name=\$$name"
        }
      }
      .let {
        if (it.isNotEmpty()) {
          "?$it"
        } else {
          it
        }
      }
    val parameterStr = parameters
      .joinToString(", ") { parameter ->
        val name = parameter.name
        val type = parameter.type
          .let {
            if (parameter.isNullable) {
              "$it?"
            } else {
              it
            }
          }
        "$name: $type"
      }
    val pathWithParameter = parameters
      .filter { !it.isNullable }
      .joinToString(RouteDivider) { parameter ->
        val name = parameter.name
        if (parameter.type == "kotlin.String") {
          encode(name)
        } else {
          "\${$name}"
        }
      }
      .let {
        if (it.isNotEmpty()) {
          "$RouteDivider$it"
        } else {
          it
        }
      }

    return "${indent}actual operator fun $name($parameterStr) = \"$parentPath$pathWithParameter${query}\""
  }

  private fun encode(value: String) = "\${java.net.URLEncoder.encode($value, \"UTF-8\")}"
  private fun encodeNullable(value: String) =
    "\${java.net.URLEncoder.encode(if($value == null) \"\" else $value, \"UTF-8\")}"
}

internal data class RouteParameter(
  val name: String,
  val type: String,
  val isNullable: Boolean = false,
)
