package me.seiko.jetpack.navigation2

import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.getViewModel

class NavControllerViewModel : ViewModel() {

  private val viewModelStores = hashMapOf<Long, ViewModelStore>()

  fun clear(id: Long) {
    viewModelStores.remove(id)?.clear()
  }

  operator fun get(id: Long): ViewModelStore {
    return viewModelStores.getOrPut(id) {
      ViewModelStore()
    }
  }

  override fun onCleared() {
    viewModelStores.forEach { it.value.clear() }
    viewModelStores.clear()
  }

  companion object {
    fun create(viewModelStore: ViewModelStore): NavControllerViewModel {
      return viewModelStore.getViewModel {
        NavControllerViewModel()
      }
    }
  }
}
