package com.mangata.sofatimexml.presentation.tvShowDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.core_ui.util.UiEvent
import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.tvShowDetail.TvShowDetails
import com.mangata.tvshow_domain.model.tvShowDetail.toTvShow
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import com.mangata.tvshow_domain.model.video.Video
import com.mangata.tvshow_domain.repository.TvShowRepository
import com.mangata.tvshow_presentation.tvShowDetail.components.headerSection.TvDetailsHeaderModel
import com.mangata.tvshow_presentation.tvShowDetail.components.headerSection.toDetailHeaderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TvShowDetailViewModel(
    private val repository: TvShowRepository,
    private val tvShowId: Int
) : ViewModel() {

    var tvShowDetailState = MutableStateFlow<TvShowDetails?>(null)
        private set

    var headerState = MutableStateFlow(TvDetailsHeaderModel())
        private set

    var videoState = MutableStateFlow<Video?>(null)
        private set

    var posterState = MutableStateFlow<List<Poster>>(emptyList())
        private set

    var similarTvShowState = MutableStateFlow<List<TvShow>>(emptyList())
        private set

    var errorState = MutableStateFlow("")
        private set

    var isLoading = MutableStateFlow(false)
        private set

    var didAllLoad = MutableStateFlow(false)
        private set

    var isAddedToWatchList = MutableStateFlow(false)
        private set

    private val eventChannel = Channel<UiEvent>(Channel.UNLIMITED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        getData()
    }

    fun onEvent(event: TvShowDetailEvent) {
        when (event) {
            is TvShowDetailEvent.AddedToWatchList -> addToWatchList()
            is TvShowDetailEvent.RemoveFromWatchlist -> deleteFromWatchlist()
        }
    }

    private fun addToWatchList() {
        viewModelScope.launch {
            tvShowDetailState.value?.let {
                launch(Dispatchers.IO) { repository.addTvShowToWatchList(it.toTvShow()) }
                eventChannel.send(UiEvent.SnackbarEvent(uiText = "Added to Watchlist"))
                isAddedToWatchList.value = true
            }
        }
    }

    private fun deleteFromWatchlist() {
        viewModelScope.launch {
            tvShowDetailState.value?.let {
                launch(Dispatchers.IO) { repository.removeTvShowFromWatchList(it.id) }
                eventChannel.send(UiEvent.SnackbarEvent(uiText = "Deleted from Watchlist"))
                isAddedToWatchList.value = false
            }
        }
    }

    private fun getData() = try {
        viewModelScope.launch {
            isLoading.value = true
            didAllLoad.value = false
            val tvShowDeferred = async { repository.getTvShowDetails(tvShowId) }
            val videoDeferred = async { repository.getVideoForTvShow(tvShowId) }
            val posterDeferred = async { repository.getImagesForTvShow(tvShowId) }
            val similarTvShowsDeferred = async { repository.getSimilarTvShows(tvShowId) }
            val watchlistDeferred = async { repository.getTrackedTvShows() }

            val tvShowDetailResult = tvShowDeferred.await()
            val videoResult = videoDeferred.await()
            val posterResult = posterDeferred.await()
            val watchList = watchlistDeferred.await()
            val similarTvShows = similarTvShowsDeferred.await()

            videoResult.onSuccess { processVideo(it) }
            posterResult.onSuccess { processPosters(it) }
            similarTvShows.onSuccess { processSimilarTvShows(it) }
            tvShowDetailResult.onSuccess { tvShow ->
                processTvShow(tvShow)
                isAddedToWatchList.value = handleWatchlistSelector(watchList, tvShow)
            }
            isLoading.value = false
            didAllLoad.value = true
        }
    } catch (e: Exception) {
        errorState.value = e.localizedMessage ?: ""
        isLoading.value = false
    }

    private fun processSimilarTvShows(tvShows: List<TvShow>) {
        similarTvShowState.value = tvShows
    }

    private fun processVideo(video: Video?) {
        videoState.value = video
    }

    private fun processPosters(posters: List<Poster>) {
        posterState.value = posters
    }

    private fun handleWatchlistSelector(watchList: List<TvShow>, remoteTvShow: TvShowDetails?) : Boolean {
        if (remoteTvShow == null) return false
        return watchList.find { it.id == remoteTvShow.id } != null
    }

    private fun processTvShow(tvShowDetails: TvShowDetails?) {
        if (tvShowDetails == null) {
            errorState.value = "Failed loading Tv Show"
            return
        }
        tvShowDetailState.value = tvShowDetails
        headerState.value = tvShowDetails.toDetailHeaderModel()
    }
}