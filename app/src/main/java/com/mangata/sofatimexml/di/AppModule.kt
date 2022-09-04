package com.mangata.sofatimexml.di

import com.mangata.sofatimexml.presentation.tvShowDetail.TvShowDetailViewModel
import com.mangata.sofatimexml.presentation.tvShowHome.TvShowHomeViewModel
import com.mangata.sofatimexml.presentation.tvShowSearch.TvShowSearchViewModel
import com.mangata.sofatimexml.presentation.tvShowTracked.TvShowTrackedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TvShowHomeViewModel(get()) }
    viewModel { params -> TvShowDetailViewModel(get(), tvShowId = params.get()) }
    viewModel { TvShowSearchViewModel(get()) }
    viewModel { TvShowTrackedViewModel(get()) }
}