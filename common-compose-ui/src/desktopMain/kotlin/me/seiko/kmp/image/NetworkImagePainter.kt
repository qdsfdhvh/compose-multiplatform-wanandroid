package me.seiko.kmp.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.seiko.extension.await
import me.seiko.kmp.storage.StorageProviderImpl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.closeQuietly
import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import javax.imageio.ImageIO

@Composable
actual fun rememberNetworkImagePainter(data: Any): Painter {
  val scope = rememberCoroutineScope { Dispatchers.IO }
  return remember(data) {
    DesktopImagePainter(
      request = data,
      parentScope = scope,
      imageCache = ImageCacheImpl.create(StorageProviderImpl.mediaCacheDir)
    )
  }
}

private class DesktopImagePainter(
  private val request: Any,
  private val parentScope: CoroutineScope,
  private val imageCache: ImageCache,
) : Painter(), RememberObserver {

  private var painter = mutableStateOf<Painter?>(null)

  private var rememberScope: CoroutineScope? = null

  override val intrinsicSize: Size
    get() = painter.value?.intrinsicSize ?: Size.Unspecified

  override fun DrawScope.onDraw() {
    painter.value?.apply {
      draw(size)
    }
  }

  override fun onAbandoned() = onForgotten()

  override fun onForgotten() {
    rememberScope?.cancel()
    rememberScope = null
  }

  override fun onRemembered() {
    rememberScope?.cancel()

    val context = parentScope.coroutineContext
    rememberScope = CoroutineScope(parentScope.coroutineContext + SupervisorJob(context[Job]))
    rememberScope!!.launch {
      if (request is String) {
        var input: InputStream? = imageCache.fetch(request)?.inputStream()
        if (input == null) {
          input = ImageLoader.load(request)?.also {
            imageCache.store(request, it)
          }
        }
        if (input != null) {
          generatePainter(input)
        }
      }
    }
  }

  private fun generatePainter(inputStream: InputStream) {
    try {
      painter.value = ImageIO.read(inputStream)?.toPainter()
    } finally {
      inputStream.closeQuietly()
    }
  }
}

private object ImageLoader {
  private val httpClient = OkHttpClient()
  suspend fun load(url: String): InputStream? {
    return httpClient.loadImage(url)
  }
}

suspend fun OkHttpClient.loadImage(url: String) = withContext(Dispatchers.IO) {
  val request = Request.Builder().url(url).build()
  val response = newCall(request).await()
  if (!response.isSuccessful) return@withContext null
  response.body?.byteStream()
}

private interface ImageCache {
  suspend fun store(url: String, inputStream: InputStream): File?
  suspend fun fetch(url: String): File?
}

private class ImageCacheImpl(
  private val cacheDir: String,
  private var maxCacheSize: Int,
  private var cacheClearRate: Float,
) : ImageCache {

  private var currentSize = -1L

  override suspend fun store(url: String, inputStream: InputStream): File? {
    val cache = File(cacheDir, md5(url))
    return try {
      if (cache.exists()) cache.delete()
      checkCacheSize()
      inputStream.use { input ->
        cache.outputStream().use { output ->
          input.copyTo(output)
        }
      }
      currentSize += cache.length()
      cache
    } catch (e: Throwable) {
      e.printStackTrace()
      null
    }
  }

  override suspend fun fetch(url: String): File? {
    val cache = File(cacheDir, md5(url))
    if (!cache.exists()) return null
    return cache
  }

  private fun checkCacheSize() {
    if (currentSize == -1L) {
      currentSize = getFolderSize(File(cacheDir))
    }
    if (currentSize.mb() > maxCacheSize) cleanCacheSize()
  }

  private fun cleanCacheSize() {
    File(cacheDir).listFiles()?.let {
      for (file in it) {
        val size = file.length()
        if (file.delete()) currentSize -= size
        if (currentSize.mb() <= maxCacheSize * (1 - cacheClearRate)) break
      }
    }
  }

  private fun getFolderSize(directory: File): Long {
    var length: Long = 0
    if (directory.isDirectory) {
      directory.listFiles()?.let {
        for (file in it) {
          length += if (file.isFile) file.length() else getFolderSize(file)
        }
      }
    } else {
      length = directory.length()
    }
    return length
  }

  private fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
  }

  private fun Long.mb() = (this / (1024 * 1024F))

  companion object {
    private val caches = mutableMapOf<String, ImageCacheImpl>()

    fun create(
      cacheDir: String,
      maxCacheSize: Int = 200,
      cacheClearRate: Float = 0.25f
    ): ImageCache {
      return caches[cacheDir]?.apply {
        this.maxCacheSize = maxCacheSize
        this.cacheClearRate = cacheClearRate
      } ?: ImageCacheImpl(cacheDir, maxCacheSize, cacheClearRate).also {
        caches[cacheDir] = it
      }
    }
  }
}
