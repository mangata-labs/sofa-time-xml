package com.mangata.tvshow_data.repository

import com.mangata.tvshow_data.local.dao.tvShow.TvShowDao
import com.mangata.tvshow_data.local.mappers.toTrackedTvShow
import com.mangata.tvshow_data.local.mappers.toTvShow
import com.mangata.tvshow_data.remote.mappers.toImage
import com.mangata.tvshow_data.remote.mappers.toTvShow
import com.mangata.tvshow_data.remote.mappers.toTvShowDetails
import com.mangata.tvshow_data.remote.mappers.toVideo
import com.mangata.tvshow_data.remote.service.TmdbService
import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.video.SourceSite
import com.mangata.tvshow_domain.model.video.Video
import com.mangata.tvshow_domain.model.video.VideoType
import com.mangata.tvshow_domain.model.tvShowList.TvShow
import com.mangata.tvshow_domain.model.tvShowDetail.TvShowDetails
import com.mangata.tvshow_domain.repository.TvShowRepository

internal class TvShowRepositoryImpl(
    private val tmdbService: TmdbService,
    private val localStorage: TvShowDao,
) : TvShowRepository {
    override suspend fun getPopularTvShows(pageNumber: Int): Result<List<TvShow>> {
        return try {
            val response = tmdbService.getPopularTvShows(pageNumber)
            val result = response.results
            Result.success(result.mapNotNull { it.toTvShow() })
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getTvShowDetails(id: Int): Result<TvShowDetails?> {
        return try {
            val response = tmdbService.getTvShowDetails(id)
            Result.success(response.toTvShowDetails())
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getVideoForTvShow(id: Int): Result<Video?> {
        return try {
            val trailers = tmdbService.getVideoForTvShow(id).toVideo()
            Result.success(trailers.firstOrNull {
                it.official &&
                        (it.sourceSite is SourceSite.YouTube) &&
                        (it.videoType is VideoType.Trailer || it.videoType is VideoType.Teaser)
            })
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getImagesForTvShow(id: Int): Result<List<Poster>> {
        return try {
            val result = tmdbService.getImagesForTvShow(id)
            val numberOfImages: Int = if (result.posters.size >= 10) 10 else result.posters.size
            Result.success(result.toImage().take(numberOfImages))
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun searchTvShows(query: String, pageNumber: Int): Result<List<TvShow>> {
        return try {
            val result = tmdbService.searchTvShows(query, pageNumber).results
            Result.success(result.mapNotNull { it.toTvShow() })
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getTrendingTvShows(): Result<List<TvShow>> {
        return try {
            val result = tmdbService.getTrendingTvShows().results
            Result.success(result.mapNotNull { it.toTvShow() }.take(10))
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun addTvShowToWatchList(tvShow: TvShow) {
        val trackedTvShow = tvShow.toTrackedTvShow()
        localStorage.addToWatchList(trackedTvShow)
    }

    override suspend fun removeTvShowFromWatchList(id: Int) {
        localStorage.deleteTrackedTvShow(id)
    }

    override suspend fun getSimilarTvShows(id: Int): Result<List<TvShow>> {
        return try {
            val result = tmdbService.getSimilarTvShows(id).results
            Result.success(result.mapNotNull { it.toTvShow() })
        } catch (e: Exception) {
            println("here error: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getTrackedTvShows(): List<TvShow> {
        return localStorage.getAllTrackedTvShows().map { it.toTvShow() }
    }
}
