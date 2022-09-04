package com.mangata.sofatimexml.presentation.tvShowDetail

sealed class TvShowDetailEvent() {
    object AddedToWatchList : TvShowDetailEvent()
    object RemoveFromWatchlist : TvShowDetailEvent()
}