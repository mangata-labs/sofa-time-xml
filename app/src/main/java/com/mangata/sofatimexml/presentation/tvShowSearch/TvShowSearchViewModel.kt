package com.mangata.sofatimexml.presentation.tvShowSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.core_ui.pager.DefaultPager
import com.mangata.tvshow_domain.repository.TvShowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvShowSearchViewModel(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    var tvShowsState = MutableStateFlow(TvShowListState())
        private set

    var searchState = MutableStateFlow("")
        private set

    private val pager = DefaultPager(
        initialKey = tvShowsState.value.page,
        onLoadUpdated = { isLoading ->
            tvShowsState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            if (searchState.value.isNotEmpty()) {
                tvShowRepository.searchTvShows(searchState.value, nextPage)
            } else {
                tvShowRepository.getPopularTvShows(nextPage)
            }
        },
        getNextKey = {
            tvShowsState.value.page + 1
        },
        onError = { error ->
            tvShowsState.update { it.copy(error = error?.localizedMessage ?: "") }
        },
        onSuccess = { items, newKey ->
            tvShowsState.update {
                it.copy(
                    tvShows = tvShowsState.value.tvShows + items,
                    page = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    init {
        loadNextTvShows()
    }

    fun onEvent(event: TvShowSearchEvent) {
        when (event) {
            is TvShowSearchEvent.EnteredSearchText -> searchState.value = event.value
            is TvShowSearchEvent.FinishedSearch -> searchTvShows()
        }
    }

    private fun searchTvShows() {
        viewModelScope.launch {
            pager.reset()
            tvShowsState.value = TvShowListState()
            pager.loadNext()
        }
    }

    fun loadNextTvShows() {
        viewModelScope.launch {
            pager.loadNext()
        }
    }
}