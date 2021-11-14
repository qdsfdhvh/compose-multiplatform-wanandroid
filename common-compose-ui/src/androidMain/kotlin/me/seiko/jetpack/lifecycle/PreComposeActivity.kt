package me.seiko.jetpack.lifecycle

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import me.seiko.jetpack.LocalBackDispatcherOwner
import me.seiko.jetpack.LocalLifecycleOwner
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.dispatcher.BackDispatcher
import me.seiko.jetpack.dispatcher.BackDispatcherOwner
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner

abstract class PreComposeActivity :
  Activity(),
  LifecycleOwner,
  ViewModelStoreOwner,
  BackDispatcherOwner,
  SavedStateRegistryOwner,
  androidx.lifecycle.LifecycleOwner {

  override val lifecycle by lazy { LifecycleRegistry() }
  override val viewModelStore by lazy { ViewModelStore() }
  override val backDispatcher by lazy { BackDispatcher() }

  private val savedStateRegistryController by lazy {
    SavedStateRegistryController.create(this)
  }

  override fun getSavedStateRegistry(): SavedStateRegistry {
    return savedStateRegistryController.savedStateRegistry
  }

  private val androidLifecycle by lazy { androidx.lifecycle.LifecycleRegistry(this) }

  override fun getLifecycle(): androidx.lifecycle.Lifecycle {
    return androidLifecycle
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    savedStateRegistryController.performRestore(savedInstanceState)
    super.onCreate(savedInstanceState)
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_CREATE)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    savedStateRegistryController.performSave(outState)
  }

  override fun onStart() {
    super.onStart()
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_START)
  }

  override fun onResume() {
    super.onResume()
    lifecycle.currentState = Lifecycle.State.Active
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_RESUME)
  }

  override fun onPause() {
    super.onPause()
    lifecycle.currentState = Lifecycle.State.InActive
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_PAUSE)
  }

  override fun onStop() {
    super.onStop()
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_STOP)
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycle.currentState = Lifecycle.State.Destroyed
    androidLifecycle.handleLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_DESTROY)
  }

  override fun onBackPressed() {
    if (!backDispatcher.onBackPress()) {
      super.onBackPressed()
    }
  }
}

fun PreComposeActivity.setContent(
  parent: CompositionContext? = null,
  content: @Composable () -> Unit
) {
  val existingComposeView = window.decorView
    .findViewById<ViewGroup>(android.R.id.content)
    .getChildAt(0) as? ComposeView

  if (existingComposeView != null) with(existingComposeView) {
    setParentCompositionContext(parent)
    setContent {
      ContentInternal(content)
    }
  } else ComposeView(this).apply {
    // Set content and parent **before** setContentView
    // to have ComposeView create the composition on attach
    setParentCompositionContext(parent)
    setContent {
      ContentInternal(content)
    }
    // Set the view tree owners before setting the content view so that the inflation process
    // and attach listeners will see them already present
    setOwners()
    setContentView(this, DefaultActivityContentLayoutParams)
  }
}

private fun PreComposeActivity.setOwners() {
  val decorView = window.decorView
  if (ViewTreeLifecycleOwner.get(decorView) == null) {
    ViewTreeLifecycleOwner.set(decorView, this)
  }
  if (ViewTreeSavedStateRegistryOwner.get(decorView) == null) {
    ViewTreeSavedStateRegistryOwner.set(decorView, this)
  }
}

@Composable
private fun PreComposeActivity.ContentInternal(content: @Composable () -> Unit) {
  ProvideAndroidCompositionLocals {
    content.invoke()
  }
}

@Composable
private fun PreComposeActivity.ProvideAndroidCompositionLocals(
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
    LocalLifecycleOwner provides this,
    LocalViewModelStoreOwner provides this,
    LocalBackDispatcherOwner provides this,
  ) {
    content.invoke()
  }
}

private val DefaultActivityContentLayoutParams = ViewGroup.LayoutParams(
  ViewGroup.LayoutParams.WRAP_CONTENT,
  ViewGroup.LayoutParams.WRAP_CONTENT
)
