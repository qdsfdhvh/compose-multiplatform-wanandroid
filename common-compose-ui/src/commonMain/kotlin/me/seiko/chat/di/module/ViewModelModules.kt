package me.seiko.chat.di.module

import me.seiko.chat.di.extension.viewModel
import me.seiko.chat.dialog.CustomDialogViewModel
import me.seiko.chat.scene.detail.DetailViewModel
import me.seiko.chat.scene.home.HomeViewModel
import me.seiko.chat.scene.home.bottom.KnowledgeViewModel
import me.seiko.chat.scene.home.bottom.MeViewModel
import me.seiko.chat.scene.home.bottom.SearchViewModel
import me.seiko.chat.scene.home.bottom.TimeLineViewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { HomeViewModel() }

  viewModel { KnowledgeViewModel() }
  viewModel { MeViewModel() }
  viewModel { SearchViewModel() }
  viewModel { TimeLineViewModel(get()) }

  viewModel { (id: Int) -> DetailViewModel(id) }

  viewModel { CustomDialogViewModel() }
}
