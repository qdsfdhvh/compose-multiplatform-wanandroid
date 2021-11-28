package me.seiko.kmp.storage

import java.io.File

interface StorageProvider {
  // for persistence data
  val appDir: String

  // for cache data
  val cacheDir: String

  // for media caches e.g image, video
  val mediaCacheDir: String

  fun String.mkdirs(): String {
    File(this).apply {
      if (!exists()) mkdirs()
    }
    return this
  }
}
