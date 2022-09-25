package com.mangata.sofatimexml.presentation.tvShowDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangata.core_ui.util.UiEvent
import com.mangata.sofatimexml.presentation.tvShowDetail.state.PosterSectionState
import com.mangata.sofatimexml.presentation.tvShowDetail.state.toDetailHeaderModel
import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.tvShowDetail.TvShowDetails
import com.mangata.tvshow_domain.model.tvShowDetail.toTvShow
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import com.mangata.tvshow_domain.model.video.Video
import com.mangata.tvshow_domain.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TvShowDetailViewModel(
    private val repository: TvShowRepository,
    private val tvShowId: Int
) : ViewModel() {

    private var tvShowDetailState = MutableStateFlow<TvShowDetails?>(null)

    val networks = tvShowDetailState.map { tvShowDetails ->
        if (tvShowDetails?.networks == null) return@map emptyList()
        else return@map tvShowDetails.networks
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val headerState = tvShowDetailState.map { tvShowDetails ->
        tvShowDetails?.toDetailHeaderModel()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val story = tvShowDetailState.map { tvShowDetails ->
        if (tvShowDetails?.overview == null) return@map ""
        else return@map tvShowDetails.overview
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    var videoState = MutableStateFlow<Video?>(null)
        private set

    var postersState = MutableStateFlow<List<Poster>>(emptyList())
        private set

    val posterSectionState = videoState.combine(postersState) { trailer, posters ->
        PosterSectionState(
            trailer = trailer,
            posters = posters
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PosterSectionState())

    var similarTvShowState = MutableStateFlow<List<TvShow>>(emptyList())
        private set

    var errorState = MutableStateFlow("")
        private set

    var isLoading = MutableStateFlow(false)
        private set

    var isAddedToWatchList = MutableStateFlow(false)
        private set

    private val eventChannel = Channel<UiEvent>(Channel.UNLIMITED)
    val eventFlow = eventChannel.receiveAsFlow()

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
        viewModelScope.launch(Dispatchers.IO) {
            tvShowDetailState.value?.let {
                repository.addTvShowToWatchList(it.toTvShow())
                eventChannel.send(UiEvent.SnackbarEvent(uiText = "Added to Watchlist"))
                isAddedToWatchList.value = true
            }
        }
    }

    private fun deleteFromWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowDetailState.value?.let {
                repository.removeTvShowFromWatchList(it.id)
                eventChannel.send(UiEvent.SnackbarEvent(uiText = "Deleted from Watchlist"))
                isAddedToWatchList.value = false
            }
        }
    }

    private fun getData() = try {
        isLoading.value = true
        viewModelScope.launch {
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
        postersState.value = posters
    }

    private fun handleWatchlistSelector(
        watchList: List<TvShow>,
        remoteTvShow: TvShowDetails?
    ): Boolean {
        if (remoteTvShow == null) return false
        return watchList.find { it.id == remoteTvShow.id } != null
    }

    private fun processTvShow(tvShowDetails: TvShowDetails?) {
        if (tvShowDetails == null) {
            errorState.value = "Failed loading Tv Show"
            return
        }
        tvShowDetailState.value = tvShowDetails
    }
}