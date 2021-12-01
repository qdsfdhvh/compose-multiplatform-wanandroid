package me.seiko.di.extension

import me.seiko.jetpack.viewmodel.ViewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.instance.newInstance
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

inline fun <reified T : ViewModel> Module.viewModel(
  qualifier: Qualifier? = null,
  noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
  return factory(qualifier, definition)
}

@OptIn(KoinReflectAPI::class)
inline fun <reified T : ViewModel> Module.viewModel(
  qualifier: Qualifier? = null
): Pair<Module, InstanceFactory<T>> {
  return factory(qualifier) { newInstance(it) }
}
