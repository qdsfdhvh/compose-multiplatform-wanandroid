package me.seiko.jetpack.lifecycle

interface LifecycleObserver {
  fun onStateChanged(state: Lifecycle.State)
}
