package me.seiko.jetpack.viewmodel

internal inline fun <reified T : ViewModel> ViewModelStore.getViewModel(
  creator: () -> T
): T {
  return getViewModel(T::class.qualifiedName.toString(), creator)
}

internal inline fun <reified T : ViewModel> ViewModelStore.getViewModel(
  key: String,
  creator: () -> T
): T {
  val existing = get(key)
  if (existing != null && existing is T) {
    return existing
  }
  val viewModel = creator()
  put(key, viewModel)
  return viewModel
}
