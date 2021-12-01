package me.seiko.jetpack.viewmodel

import kotlin.reflect.KClass

inline fun <reified T : ViewModel> ViewModelStore.getViewModel(
  key: String = T::class.qualifiedName.toString(),
  noinline creator: () -> T
): T {
  return getViewModel(key, T::class, creator)
}

fun <T : ViewModel> ViewModelStore.getViewModel(
  key: String,
  clazz: KClass<T>,
  creator: () -> T
): T {
  val existing = get(key)
  if (existing != null && clazz.isInstance(existing)) {
    @Suppress("UNCHECKED_CAST")
    return existing as T
  }
  val viewModel = creator()
  put(key, viewModel)
  return viewModel
}
