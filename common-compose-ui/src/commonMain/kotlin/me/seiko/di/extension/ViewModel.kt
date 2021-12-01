package me.seiko.di.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import me.seiko.jetpack.LocalViewModelStoreOwner
import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.jetpack.viewmodel.ViewModelStoreOwner
import me.seiko.jetpack.viewmodel.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools
import kotlin.reflect.KClass

@Composable
inline fun <reified T : ViewModel> getViewModel(
  qualifier: Qualifier? = null,
  noinline parameters: ParametersDefinition? = null,
) = getViewModel(qualifier, T::class, parameters)

@Composable
fun <T : ViewModel> getViewModel(
  qualifier: Qualifier? = null,
  clazz: KClass<T>,
  parameters: ParametersDefinition? = null,
): T {
  val owner = LocalViewModelStoreOwner.current
  return remember(qualifier, parameters) {
    owner.getViewModel(qualifier, clazz, parameters)
  }
}

fun <T : ViewModel> ViewModelStoreOwner.getViewModel(
  qualifier: Qualifier? = null,
  clazz: KClass<T>,
  parameters: ParametersDefinition? = null,
): T {
  return viewModelStore.getViewModel(qualifier?.value ?: clazz.toString(), clazz) {
    KoinPlatformTools.defaultContext().get().get(clazz, qualifier, parameters)
  }
}
