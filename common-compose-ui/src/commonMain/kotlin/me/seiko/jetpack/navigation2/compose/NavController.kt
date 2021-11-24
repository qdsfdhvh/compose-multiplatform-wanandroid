package me.seiko.jetpack.navigation2.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.collect
import me.seiko.jetpack.navigation2.NavBackStackEntry
import me.seiko.jetpack.navigation2.NavController

@Composable
fun rememberNavController(): NavController {
  return remember {
    NavController().apply {
      navigatorProvider.put(ComposeNavigator())
      navigatorProvider.put(DialogNavigator())
    }
  }
}

@Composable
fun NavController.collectBackStackEntryAsState(): State<NavBackStackEntry?> {
  val currentBackStackState = mutableStateOf<NavBackStackEntry?>(null)
  LaunchedEffect(this) {
    snapshotFlow { currentBackStack }.collect {
      currentBackStackState.value = it
    }
  }
  return currentBackStackState
}

@Composable
fun NavController.collectBackStackQueueAsState(): SnapshotStateList<NavBackStackEntry> {
  return backStackQueue
}
