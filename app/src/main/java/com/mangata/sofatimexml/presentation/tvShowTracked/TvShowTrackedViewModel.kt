package com.mangata.sofatimexml.presentation.tvShowTracked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import com.mangata.tvshow_domain.repository.TvShowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TvShowTrackedViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    val state = MutableStateFlow(TrackedTvShowState())

    fun getTrackedTvShow() {
        state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.getTrackedTvShows()
            state.update { it.copy(isLoading = false, tvShows = result) }
        }
    }
}

data class TrackedTvShowState(
    val isLoading: Boolean = false,
    val tvShows: List<TvShow> = emptyList()
)