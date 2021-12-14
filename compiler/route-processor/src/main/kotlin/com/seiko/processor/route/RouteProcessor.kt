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

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import java.io.OutputStream

class RouteProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return RouteProcessor(environment)
  }
}

class RouteProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private val codeGenerator = environment.codeGenerator
  private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val routeSymbol = resolver
      .getSymbolsWithAnnotation(
        AppRoute::class.qualifiedName
          ?: throw CloneNotSupportedException("Can not get qualifiedName for com.seiko.processor.route.AppRoute")
      )
      .filterIsInstance<KSClassDeclaration>()

    routeSymbol.forEach {
      it.accept(RouteVisitor(), routeSymbol.toList())
    }
    return emptyList()
  }

  inner class RouteVisitor : KSEmptyVisitor<List<KSClassDeclaration>, Unit>() {
    override fun defaultHandler(node: KSNode, data: List<KSClassDeclaration>) {
      if (node !is KSClassDeclaration) {
        return
      }

      val annotation = node.annotations
        .firstOrNull { it.annotationType.resolve().declaration.qualifiedName?.asString() == AppRoute::class.qualifiedName }
        ?: return

      val schema = annotation.getStringValue(AppRoute::schema.name) ?: ""
      val packageName = annotation.getStringValue(AppRoute::packageName.name)
        ?: node.packageName.asString()
      // val routeClassName = annotation.getStringValue(AppRoute::routeClassName.name)
      //   ?: "${node.qualifiedName?.getShortName()}Route"
      // val definitionClassName = annotation.getStringValue(AppRoute::definitionClassName.name)
      //   ?: "${node.qualifiedName?.getShortName()}RouteDefinition"
      val routeClassName = node.qualifiedName!!.getShortName()

      val route = generateRoute(declaration = node)
        .takeIf {
          it is NestedRouteDefinition
        }?.let {
          PrefixRouteDefinition(
            schema = schema,
            child = it as NestedRouteDefinition,
            routeClassName = routeClassName,
            // definitionClassName = definitionClassName,
          )
        } ?: return

      val dependencies = Dependencies(
        true,
        *(data.mapNotNull { it.containingFile } + listOfNotNull(node.containingFile)).toTypedArray()
      )
      generateFile(
        dependencies,
        packageName,
        routeClassName,
        route.generateRoute()
      )
      // generateFile(
      //   dependencies,
      //   packageName,
      //   definitionClassName,
      //   route.generateDefinition()
      // )
    }

    private fun generateFile(
      dependencies: Dependencies,
      packageName: String,
      className: String,
      content: String
    ) {
      codeGenerator.createNewFile(
        dependencies,
        packageName,
        className
      ).use { outputStream ->
        outputStream.appendLine("package $packageName")
        outputStream.appendLine()
        outputStream.appendLine(content)
      }
    }

    private fun generateRoute(
      declaration: KSDeclaration,
      parent: RouteDefinition? = null
    ): RouteDefinition {
      val name = declaration.simpleName.getShortName()

      logger.warn("declaration=$name")

      return when (declaration) {
        is KSClassDeclaration -> {
          NestedRouteDefinition(
            name = name,
            parent = parent,
            fullName = declaration.qualifiedName?.asString() ?: "<ERROR>"
          ).also { nestedRouteDefinition ->
            nestedRouteDefinition.childRoute.addAll(
              declaration.declarations
                .filter { it.simpleName.getShortName() != "<init>" }
                .map {
                  generateRoute(it, nestedRouteDefinition)
                }
            )
          }
        }
        is KSPropertyDeclaration -> {
          ConstRouteDefinition(name, parent)
        }
        is KSFunctionDeclaration -> {
          FunctionRouteDefinition(
            name = name,
            parent = parent,
            parameters = declaration.parameters.map {
              val parameterName = it.name?.getShortName() ?: "_"
              val parameterType = it.type.resolve()
              RouteParameter(
                name = parameterName,
                type = parameterType.declaration.qualifiedName?.asString()
                  ?: "<ERROR>",
                isNullable = parameterType.isMarkedNullable,
              )
            },
          )
        }
        else -> throw NotImplementedError()
      }
    }
  }
}

private fun OutputStream.appendLine(str: String = "") {
  this.write("$str${System.lineSeparator()}".toByteArray())
}

private fun KSAnnotation.getStringValue(name: String): String? = arguments
  .firstOrNull { it.name?.asString() == name }
  ?.let { it.value as? String? }.takeIf { !it.isNullOrEmpty() }
