package me.seiko.jetpack.navigation2

import kotlin.reflect.KClass

class NavigatorProvider {

  private val _navigators = mutableMapOf<KClass<out Navigator>, Navigator>()

  fun <T : Navigator> get(navigatorClass: KClass<T>): T? {
    @Suppress("UNCHECKED_CAST")
    return _navigators[navigatorClass] as? T
  }

  fun <T : Navigator> put(navigator: T) {
    _navigators[navigator::class] = navigator
  }
}
