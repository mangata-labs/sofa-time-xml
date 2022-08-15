package com.mangata.tvshow_domain.repository

import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.video.Video
import com.mangata.tvshow_domain.model.tvShowList.TvShow
import com.mangata.tvshow_domain.model.tvShowDetail.TvShowDetails

interface TvShowRepository {
    suspend fun getPopularTvShows(pageNumber: Int) : Result<List<TvShow>>
    suspend fun getTvShowDetails(id: Int) : Result<TvShowDetails?>
    suspend fun getVideoForTvShow(id: Int) : Result<Video?>
    suspend fun getImagesForTvShow(id: Int) : Result<List<Poster>>
    suspend fun searchTvShows(query: String, pageNumber: Int) : Result<List<TvShow>>
    suspend fun getTrendingTvShows() : Result<List<TvShow>>
    suspend fun getTrackedTvShows() : List<TvShow>
    suspend fun addTvShowToWatchList(tvShow: TvShow)
    suspend fun removeTvShowFromWatchList(id: Int)
    suspend fun getSimilarTvShows(id: Int) : Result<List<TvShow>>
}