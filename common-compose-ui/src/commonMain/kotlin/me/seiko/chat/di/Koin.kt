package me.seiko.chat.di

import me.seiko.chat.di.module.httpModule
import me.seiko.chat.di.module.serviceModule
import me.seiko.chat.di.module.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
  appDeclaration()

  modules(httpModule)
  modules(serviceModule)
  modules(viewModelModule)
}
