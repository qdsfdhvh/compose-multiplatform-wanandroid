package me.seiko.chat.android

import android.app.Application
import me.seiko.chat.di.initKoin
import me.seiko.util.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AndroidApp : Application() {
  override fun onCreate() {
    super.onCreate()
    Logger.init()
    initKoin {
      androidContext(this@AndroidApp)
      androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
    }
  }
}
