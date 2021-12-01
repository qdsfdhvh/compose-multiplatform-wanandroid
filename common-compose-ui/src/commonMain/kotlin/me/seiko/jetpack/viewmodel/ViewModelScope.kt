package me.seiko.jetpack.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import me.seiko.jetpack.disposable.Disposable
import kotlin.coroutines.CoroutineContext


private const val JOB_KEY = " me.seiko.jetpack.viewmodel.ViewModelCoroutineScope.JOB_KEY"

val ViewModel.viewModelScope: CoroutineScope
  get() {
    val scope: CoroutineScope? = this.getTag(JOB_KEY)
    if (scope != null) {
      return scope
    }
    return setTagIfAbsent(
      JOB_KEY,
      CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    )
  }

internal class CloseableCoroutineScope(context: CoroutineContext) : Disposable, CoroutineScope {

  override val coroutineContext: CoroutineContext = context

  override fun cancel() {
    coroutineContext.cancel()
  }
}
