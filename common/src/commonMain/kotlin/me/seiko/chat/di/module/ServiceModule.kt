package me.seiko.chat.di.module

import io.ktor.client.HttpClient
import me.seiko.chat.service.wanandroid.WanAndroidService
import org.koin.dsl.module

val serviceModule = module {
  single { provideWanAndroidService(get()) }
}

private fun provideWanAndroidService(httpClient: HttpClient): WanAndroidService {
  return WanAndroidService(httpClient)
}
