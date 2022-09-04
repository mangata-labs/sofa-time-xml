package com.mangata.sofatimexml.presentation.tvShowHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.core_ui.util.UiEvent
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import com.mangata.tvshow_domain.repository.TvShowRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TvShowHomeViewModel(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    var tvShowsState = MutableStateFlow<List<TvShow>>(emptyList())
        private set

    var isLoading = MutableStateFlow(false)
        private set

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    init {
        loadTrendingTvShows()
    }

    private fun loadTrendingTvShows() {
        viewModelScope.launch {
            isLoading.value = true
            val result = tvShowRepository.getTrendingTvShows()
            result.onSuccess {
                tvShowsState.value = it
                isLoading.value = false
            }
            result.onFailure {
                channel.send(UiEvent.SnackbarEvent(it.localizedMessage ?: ""))
            }
        }
    }
}