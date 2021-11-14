/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 *
 *  This file is part of Twidere X.
 *
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package me.seiko.jetpack.navigation

import me.seiko.jetpack.viewmodel.ViewModel
import me.seiko.jetpack.viewmodel.ViewModelStore
import me.seiko.jetpack.viewmodel.getViewModel

internal class NavControllerViewModel : ViewModel() {

  private val viewModelStores = hashMapOf<Long, ViewModelStore>()

  fun clear(id: Long) {
    viewModelStores.remove(id)?.clear()
  }

  fun get(id: Long): ViewModelStore {
    return viewModelStores.getOrPut(id) {
      ViewModelStore()
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelStores.iterator().forEach { it.value.clear() }
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
