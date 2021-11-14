package me.seiko.jetpack.viewmodel

import me.seiko.jetpack.disposable.Disposable

abstract class ViewModel {

  private val mBagOfTags = hashMapOf<String, Any>()

  @Volatile
  private var mCleared = false

  open fun onCleared() {}

  fun clear() {
    mCleared = true
    for (value in mBagOfTags.values) {
      closeWithRuntimeException(value)
    }
    onCleared()
  }

  open fun <T> setTagIfAbsent(key: String, newValue: T): T {
    @Suppress("UNCHECKED_CAST")
    return mBagOfTags.getOrPut(key) {
      newValue as Any
    }.also {
      if (mCleared) {
        closeWithRuntimeException(it)
      }
    } as T
  }

  fun <T> getTag(key: String): T? {
    @Suppress("UNCHECKED_CAST")
    return mBagOfTags[key] as? T
  }
}

private fun closeWithRuntimeException(obj: Any) {
  if (obj is Disposable) {
    obj.cancel()
  }
}
