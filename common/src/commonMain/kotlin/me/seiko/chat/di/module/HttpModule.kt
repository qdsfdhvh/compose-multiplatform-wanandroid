package me.seiko.chat.di.module

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import me.seiko.util.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val httpModule = module {
  single { provideOkHttpClient() }
  single { provideHttpClient(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder()
    .addInterceptor(
      HttpLoggingInterceptor { msg ->
        Logger.i { msg }
      }.apply {
        level = HttpLoggingInterceptor.Level.BODY
      }
    )
    .build()
}

private fun provideHttpClient(client: OkHttpClient): HttpClient {
  return HttpClient(OkHttp) {
    engine {
      preconfigured = client
    }

    install(JsonFeature) {
      serializer = KotlinxSerializer(
        Json {
          prettyPrint = true
          isLenient = true
        }
      )
    }
  }
}
