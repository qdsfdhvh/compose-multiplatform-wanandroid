package me.seiko.di.module

import me.seiko.chat.dialog.CustomDialogViewModel
import me.seiko.chat.scene.detail.DetailViewModel
import me.seiko.chat.scene.home.HomeViewModel
import me.seiko.chat.scene.home.bottom.MeViewModel
import me.seiko.chat.scene.home.bottom.MentionViewModel
import me.seiko.chat.scene.home.bottom.SearchViewModel
import me.seiko.chat.scene.home.bottom.TimeLineViewModel
import me.seiko.di.extension.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { HomeViewModel() }

  viewModel { MentionViewModel() }
  viewModel { MeViewModel() }
  viewModel { SearchViewModel() }
  viewModel { TimeLineViewModel() }

  viewModel { (id: Int) -> DetailViewModel(id) }

  viewModel { CustomDialogViewModel() }
}
