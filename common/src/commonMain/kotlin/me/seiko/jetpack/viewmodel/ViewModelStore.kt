package me.seiko.jetpack.viewmodel

class ViewModelStore {

  private val mMap = hashMapOf<String, ViewModel>()

  fun put(key: String, viewModel: ViewModel) {
    val oldViewModel = mMap.put(key, viewModel)
    oldViewModel?.onCleared()
  }

  operator fun get(key: String): ViewModel? {
    return mMap[key]
  }

  fun keys(): Set<String> {
    return HashSet(mMap.keys)
  }

  fun clear() {
    for (vm in mMap.values) {
      vm.clear()
    }
    mMap.clear()
  }
}
