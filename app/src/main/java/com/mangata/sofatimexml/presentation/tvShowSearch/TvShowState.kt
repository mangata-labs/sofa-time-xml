package com.mangata.sofatimexml.presentation.tvShowSearch

import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow

data class TvShowListState(
    val tvShows: List<TvShow> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 1,
)