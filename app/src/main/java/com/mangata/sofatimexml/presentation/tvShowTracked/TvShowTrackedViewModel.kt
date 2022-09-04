package com.mangata.sofatimexml.presentation.tvShowTracked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import com.mangata.tvshow_domain.repository.TvShowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TvShowTrackedViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    var state = MutableStateFlow<List<TvShow>>(emptyList())
        private set

    fun getTrackedTvShow() {
        viewModelScope.launch {
            val result = repository.getTrackedTvShows()
            state.value = result
        }
    }
}