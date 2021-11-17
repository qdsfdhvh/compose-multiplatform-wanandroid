package me.seiko.jetpack

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import me.seiko.jetpack.dispatcher.BackDispatcherOwner
import me.seiko.jetpack.lifecycle.LifecycleOwner
import me.seiko.jetpack.navigation.NavController
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner

val LocalLifecycleOwner = compositionLocalOf<LifecycleOwner?> { null }
val LocalViewModelStoreOwner = compositionLocalOf<ViewModelStoreOwner?> { null }
val LocalBackDispatcherOwner = compositionLocalOf<BackDispatcherOwner?> { null }
val LocalNavController = staticCompositionLocalOf<NavController> { error("No NavController") }
