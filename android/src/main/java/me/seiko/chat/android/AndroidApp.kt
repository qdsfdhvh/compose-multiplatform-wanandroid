package me.seiko.chat.android

import android.app.Application
import me.seiko.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApp : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin {
      androidContext(this@AndroidApp)
      androidLogger()
    }
  }
}
